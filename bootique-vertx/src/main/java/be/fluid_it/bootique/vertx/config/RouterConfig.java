package be.fluid_it.bootique.vertx.config;

public class RouterConfig {
    private StaticHandlerConfig staticHandler;
    private SockJSHandlerConfig sockjs;

    public StaticHandlerConfig staticHandler() {
        return staticHandler;
    }

    public boolean staticHandlerDefined() {
        return staticHandler != null;
    }

    public void setStatic(StaticHandlerConfig staticHandler) {
        this.staticHandler = staticHandler;
    }

    public SockJSHandlerConfig sockjs() {
        return sockjs;
    }

    public void setSockjs(SockJSHandlerConfig sockjs) {
        this.sockjs = sockjs;
    }
}
