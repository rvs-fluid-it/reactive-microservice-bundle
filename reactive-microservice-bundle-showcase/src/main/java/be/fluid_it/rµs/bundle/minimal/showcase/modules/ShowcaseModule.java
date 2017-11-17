package be.fluid_it.rµs.bundle.minimal.showcase.modules;

import be.fluid_it.bootique.vertx.VertxModule;
import be.fluid_it.rµs.bundle.minimal.showcase.domain.CounterService;
import be.fluid_it.rµs.bundle.minimal.showcase.services.SimpleCounterService;
import be.fluid_it.rµs.bundle.minimal.showcase.verticles.CounterVerticle;
import com.google.inject.Binder;
import com.google.inject.Module;

public class ShowcaseModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(CounterService.class).to(SimpleCounterService.class);
        VertxModule.extend(binder).addVerticle(CounterVerticle.class);
    }
}
