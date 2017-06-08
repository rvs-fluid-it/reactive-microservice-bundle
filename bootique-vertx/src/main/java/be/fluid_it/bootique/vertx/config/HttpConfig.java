package be.fluid_it.bootique.vertx.config;

import io.bootique.annotation.BQConfigProperty;

public class HttpConfig {
    private HttpServerConfig server;

    /**
     * @param server The http server started by VertX
     */
    @BQConfigProperty
    public void setServer(HttpServerConfig server) {
        this.server = server;
    }

    public HttpServerConfig server() {
        return server;
    }
}
