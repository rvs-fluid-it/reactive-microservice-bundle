package be.fluid_it.bootique.vertx;

import be.fluid_it.bootique.vertx.config.BridgeConfig;
import be.fluid_it.bootique.vertx.config.HttpConfig;
import be.fluid_it.bootique.vertx.config.RouterConfig;
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
    private HttpConfig http;
    private RouterConfig router;
    private BridgeConfig bridge;

    /**
     * @param http The http services
     */
    @BQConfigProperty
    public void setHttp(HttpConfig http) {
        this.http = http;
    }

    public HttpConfig http() {
        return http;
    }

    /**
     * @param bridge The configuration of the Eventbus SocketJS bridge
     */
    @BQConfigProperty
    public void setBridge(BridgeConfig bridge) {
        this.bridge = bridge;
    }

    public BridgeConfig bridge() {
        return bridge;
    }

    /**
     * @param router The configuration of the router
     */
    @BQConfigProperty
    public void setRouter(RouterConfig router) {
        this.router = router;
    }

    public RouterConfig router() {
        return router;
    }

    public boolean isRouterDefined() {
        return this.router != null;
    }

    public Vertx createVertxEngine(Set<? extends Verticle> verticles) {
        Vertx vertx = Vertx.vertx();
        verticles.forEach(v -> vertx.deployVerticle(v));
        return vertx;
    }
}
