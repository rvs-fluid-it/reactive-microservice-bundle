package be.fluid_it.bootique.vertx.config;

public class StaticHandlerConfig {
    private String path;
    private String root;

    public StaticHandlerConfig(String dummy) {
    }

    public String path() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String root() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
