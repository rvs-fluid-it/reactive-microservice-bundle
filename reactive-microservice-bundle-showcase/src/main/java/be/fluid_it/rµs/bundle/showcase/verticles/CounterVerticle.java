package be.fluid_it.rµs.bundle.showcase.verticles;

import be.fluid_it.rµs.bundle.showcase.domain.CounterService;
import com.google.inject.name.Named;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import javax.inject.Inject;
import java.util.HashMap;

public class CounterVerticle extends AbstractVerticle {
    private final CounterService counterService;
    private int httpServerPort;

    @Inject
    public CounterVerticle(CounterService counterService, @Named(value = "port") int httpServerPort) {
        this.counterService = counterService;
        this.httpServerPort = httpServerPort;
    }

    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        // Allow events for the designated addresses in/out of the event bus bridge
        BridgeOptions opts = new BridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress("counter::actions"))
                .addInboundPermitted(new PermittedOptions().setAddress("counter::total"))
                .addOutboundPermitted(new PermittedOptions().setAddress("counter::actions"));

        // Create the event bus bridge and add it to the router.
        SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts, bridgeEvent -> {
            JsonObject message = bridgeEvent.getRawMessage();
            if (bridgeEvent.type() == BridgeEventType.PUBLISH) {
                System.out.println(message.toString());
                counterService.handleEvent(message);
            }
            bridgeEvent.complete(true);
        });
        router.route("/eventbus/*").handler(ebHandler);

        // Start the web server and tell it to use the router to handle requests.
        vertx.createHttpServer().requestHandler(router::accept).listen(httpServerPort);

        EventBus eb = vertx.eventBus();

        // Register to listen for messages coming IN to the server
        eb.consumer("counter::total").handler(message -> {
            Object action = message.body();
            message.reply(new HashMap<String, Integer>() {{ put("total", counterService.total()); }});
        });
    }
}
