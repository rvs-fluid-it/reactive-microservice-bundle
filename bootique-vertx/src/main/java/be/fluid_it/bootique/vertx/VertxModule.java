package be.fluid_it.bootique.vertx;

import be.fluid_it.bootique.vertx.command.EngineCommand;
import be.fluid_it.bootique.vertx.config.PermissionsConfig;
import be.fluid_it.bootique.vertx.filter.ChannelsDiscoverableFilter;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.bootique.BQCoreModule;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.vertx.core.Handler;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;

import java.util.Set;

public class VertxModule extends ConfigModule {

    public static final String PREFIX = "vertx";

    public static VertxModuleExtender extend(Binder binder) {
        return new VertxModuleExtender(binder);
    }

    public VertxModule() {
    }

    public VertxModule(String configPrefix) {
        super(configPrefix);
    }

    @Override
    protected String defaultConfigPrefix() {
        return PREFIX;
    }

    @Override
    public void configure(Binder binder) {
        BQCoreModule.extend(binder).addCommand(EngineCommand.class);
        VertxModule.extend(binder).initAllExtensions();
    }

    @Singleton
    @Provides
    public Vertx provideVertxEngine(VertxFactory vertxFactory, Set<Verticle> verticles) {
        return vertxFactory.createVertxEngine(verticles);
    }

    @Singleton
    @Provides
    public VertxFactory provideVertxFactory(ConfigurationFactory configFactory) {
        return configFactory.config(VertxFactory.class, configPrefix);
    }

    @Singleton
    @Provides
    @Named (value = "port")
    public int provideHttpServerPort(VertxFactory vertxFactory) {
        return vertxFactory.http() != null ?
                ( vertxFactory.http().server() != null ? vertxFactory.http().server().port() : 8080 ) :
                8080;
    }

    @Singleton
    @Provides
    public BridgeOptions provideBridgeOptions(VertxFactory vertxFactory) {
        PermissionsConfig permissionsConfig = vertxFactory.router().sockjs().bridge().permissions();
        BridgeOptions opts = new BridgeOptions();
        for (String inboundPermmited : permissionsConfig.inbound()) {
            opts.addInboundPermitted(new PermittedOptions().setAddress(inboundPermmited));
        }
        for (String outboundPermmited : permissionsConfig.outbound()) {
            opts.addOutboundPermitted(new PermittedOptions().setAddress(outboundPermmited));
        }
        return opts;
    }

    @Singleton
    @Provides
    @Named("channels.discoverable")
    public boolean provideChannelsDiscoverable(VertxFactory vertxFactory) {
        return vertxFactory.router().sockjs().channels().isDiscoverable();
    }


    @Singleton
    @Provides
    @Named("channels.service.name")
    public String provideChannelsServiceName(VertxFactory vertxFactory) {
        return vertxFactory.router().sockjs().channels().serviceName();
    }

    @Singleton
    @Provides
    public Router provideRouter(VertxFactory vertxFactory,
                                Vertx vertx,
                                BridgeOptions bridgeOptions,
                                Handler<BridgeEvent> bridgeEventHandler,
                                @Named("channels.discoverable") boolean channelsDiscoverable,
                                @Named("channels.service.name") String channelsServiceName) {
        io.vertx.rxjava.core.Vertx rxVertx = new io.vertx.rxjava.core.Vertx(vertx);
        Router router = Router.router(rxVertx);

        Handler<BridgeEvent> decoratedBridgeEventHandler = bridgeEventHandler;
        if (channelsDiscoverable) {
            decoratedBridgeEventHandler = new ChannelsDiscoverableFilter(decoratedBridgeEventHandler, vertx, channelsServiceName);
        }
        SockJSHandler sjsHandler = SockJSHandler.create(rxVertx).bridge(bridgeOptions, decoratedBridgeEventHandler);
        Route sockJSRoute = null;
        if (vertxFactory.router().sockjs().path() != null) {
            sockJSRoute = router.route(vertxFactory.router().sockjs().path());
        } else {
            sockJSRoute = router.route();
        }
        sockJSRoute.handler(sjsHandler);
        if (vertxFactory.router().staticHandlerDefined()) {
            Route staticRoute = null;
            if (vertxFactory.router().staticHandler().path() != null)  {
                staticRoute = router.route(vertxFactory.router().staticHandler().path());
            } else {
                staticRoute = router.route();
            }
            staticRoute.handler(
                    vertxFactory.router().staticHandler().root() != null ?
                            StaticHandler.create(vertxFactory.router().staticHandler().root()) :
                            StaticHandler.create());
        }
        return router;
    }
}
