package be.fluid_it.bootique.vertx.config;

import io.bootique.annotation.BQConfigProperty;

public class StaticHandlerConfig {
    private String path;
    private String root;

    public StaticHandlerConfig() {
    }
    public StaticHandlerConfig(String dummy) {
    }

    public String path() {
        return path;
    }

    @BQConfigProperty
    public void setPath(String path) {
        this.path = path;
    }

    public String root() {
        return root;
    }

    @BQConfigProperty
    public void setRoot(String root) {
        this.root = root;
    }
}
