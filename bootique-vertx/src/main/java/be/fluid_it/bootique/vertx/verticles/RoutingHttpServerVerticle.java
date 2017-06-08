package be.fluid_it.bootique.vertx.verticles;

import com.google.inject.Provider;
import com.google.inject.name.Named;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;

import javax.inject.Inject;


public class RoutingHttpServerVerticle extends AbstractVerticle {
    private int httpServerPort;
    private Provider<Router> routerProvider;

    @Inject
    public RoutingHttpServerVerticle(@Named(value = "port") int httpServerPort,
                                     Provider<Router> routerProvider) {
        this.httpServerPort = httpServerPort;
        this.routerProvider = routerProvider;
    }

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer().requestHandler(routerProvider.get()::accept);
        server.rxListen(httpServerPort).subscribe();
    }
}
