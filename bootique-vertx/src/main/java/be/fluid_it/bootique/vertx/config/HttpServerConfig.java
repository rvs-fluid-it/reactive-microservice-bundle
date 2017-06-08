package be.fluid_it.bootique.vertx.config;

import io.bootique.annotation.BQConfigProperty;

public class HttpServerConfig {
    private int port = 8080;

    /**
     * @param port The port for the http server
     */
    @BQConfigProperty
    public void setPort(int port) {
        this.port = port;
    }

    public int port() {
        return port;
    }
}
