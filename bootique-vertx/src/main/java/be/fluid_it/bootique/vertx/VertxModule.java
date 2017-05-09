package be.fluid_it.bootique.vertx;

import be.fluid_it.bootique.vertx.command.EngineCommand;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.bootique.BQCoreModule;
import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

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
        return vertxFactory.httpServerPort();
    }

}
