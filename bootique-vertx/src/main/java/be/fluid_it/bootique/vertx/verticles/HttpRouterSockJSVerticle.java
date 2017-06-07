package be.fluid_it.bootique.vertx.verticles;

import com.google.inject.name.Named;
import io.vertx.core.Handler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.rxjava.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;

import javax.inject.Inject;


public class HttpRouterSockJSVerticle extends AbstractVerticle {
    private int httpServerPort;
    private BridgeOptions bridgeOptions;
    private Handler<BridgeEvent> bridgeEventHandler;

    @Inject
    public HttpRouterSockJSVerticle(@Named(value = "port") int httpServerPort,
                                    BridgeOptions bridgeOptions,
                                    Handler<BridgeEvent> bridgeEventHandler) {
        this.httpServerPort = httpServerPort;
        this.bridgeOptions = bridgeOptions;
        this.bridgeEventHandler = bridgeEventHandler;
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        SockJSHandler sjsHandler = SockJSHandler.create(vertx).bridge(bridgeOptions, bridgeEventHandler);
        router.route("/eventbus/*").handler(sjsHandler);
        router.route().handler(StaticHandler.create());
        HttpServer server = vertx.createHttpServer().requestHandler(router::accept);
        server.rxListen(httpServerPort).subscribe();
    }
}
