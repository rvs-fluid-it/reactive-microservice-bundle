package be.fluid_it.bootique.vertx;

import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import java.util.Set;

/**
 * A YAML-configurable factory of Vertx engine.
 */
@BQConfig("Configures the Vertx engine.")
public class VertxFactory {
    private int httpServerPort;

    /**
     *
     * @param httpServerPort The port for the http server
     */
    @BQConfigProperty
    public void setHttpServerPort(int httpServerPort) {
        this.httpServerPort = httpServerPort;
    }

    public Vertx createVertxEngine(Set<? extends Verticle> verticles) {
        System.out.println("httpServerPort = " + httpServerPort + " ...");
        Vertx vertx = Vertx.vertx();
        verticles.forEach( v -> vertx.deployVerticle(v));
        return vertx;
    }
}
