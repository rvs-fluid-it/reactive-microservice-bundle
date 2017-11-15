package be.fluid_it.rµs.bundle.minimal.showcase.verticles;

import com.google.inject.Provider;
import io.vertx.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class HelloVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Provider<Router> routerProvider;

    @Inject
    public HelloVerticle(Provider<Router> routerProvider) {
        this.routerProvider = routerProvider;
    }

    @Override
    public void start() throws Exception {
        logger.info("Starting verticle ...");
        routerProvider.get().route("/").handler(routingContext -> {
            routingContext.response().end("Hello world!");
        });
    }
}
