package be.fluid_it.bootique.vertx;

import be.fluid_it.bootique.vertx.command.EngineCommand;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
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
    public Vertx provideVertxEngine(ConfigurationFactory configFactory, Set<Verticle> verticles) {
        return configFactory.config(VertxFactory.class, configPrefix).createVertxEngine(verticles);
    }
}
