package be.fluid_it.bootique.vertx;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;
import io.vertx.core.spi.VertxFactory;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

public class VertxModuleProvider implements BQModuleProvider {
    public Module module() {
        return new VertxModule();
    }

    @Override
    public Map<String, Type> configs() {
        return Collections.singletonMap(VertxModule.PREFIX, VertxFactory.class);
    }
}