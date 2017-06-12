package be.fluid_it.bootique.vertx.config;

public class RouterConfig {
    private StaticHandlerConfig staticHandler;
    private SockJSHandlerConfig sockjs;

    public StaticHandlerConfig staticHandler() {
        return staticHandler;
    }

    public void setStatic(StaticHandlerConfig staticHandler) {
        this.staticHandler = staticHandler;
    }

    public boolean isStaticHandlerDefined() {
        return this.staticHandler != null;
    }

    public SockJSHandlerConfig sockjs() {
        return sockjs;
    }

    public void setSockjs(SockJSHandlerConfig sockjs) {
        this.sockjs = sockjs;
    }

    public boolean isSockJSHandlerDefined() {
        return this.sockjs != null;
    }
}
