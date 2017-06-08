package be.fluid_it.bootique.vertx.config;

public class SockJSHandlerConfig {
    private String path;
    private BridgeConfig bridge;

    public String path() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BridgeConfig bridge() {
        return bridge;
    }

    public void setBridge(BridgeConfig bridge) {
        this.bridge = bridge;
    }
}
