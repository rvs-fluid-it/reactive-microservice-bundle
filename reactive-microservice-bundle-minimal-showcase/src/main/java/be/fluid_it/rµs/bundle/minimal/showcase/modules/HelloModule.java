package be.fluid_it.rµs.bundle.minimal.showcase.modules;

import be.fluid_it.bootique.vertx.VertxModule;
import be.fluid_it.rµs.bundle.minimal.showcase.verticles.HelloVerticle;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Providers;
import io.vertx.core.Handler;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;

public class HelloModule implements Module{
    @Override
    public void configure(Binder binder) {
        VertxModule.extend(binder)
                .addVerticle(HelloVerticle.class);
        binder.bind(new TypeLiteral<Handler<BridgeEvent>>(){}).toProvider(Providers.of(null));
    }
}
