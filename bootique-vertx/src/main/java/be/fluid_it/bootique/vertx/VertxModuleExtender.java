package be.fluid_it.bootique.vertx;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import io.bootique.ModuleExtender;
import io.vertx.core.Verticle;

public class VertxModuleExtender extends ModuleExtender<VertxModuleExtender> {
    private Multibinder<Verticle> verticles;

    public VertxModuleExtender(Binder binder) {
        super(binder);
    }

    @Override
    public VertxModuleExtender initAllExtensions() {
        contributeVerticles();
        return null;
    }

    public VertxModuleExtender addVerticle(Class<? extends Verticle> verticleClazz) {
        contributeVerticles().addBinding().to(verticleClazz);
        return this;
    }

    private Multibinder<Verticle> contributeVerticles() {
        if (verticles == null) {
            verticles = newSet(Verticle.class);
        }
        return verticles;
    }
}
