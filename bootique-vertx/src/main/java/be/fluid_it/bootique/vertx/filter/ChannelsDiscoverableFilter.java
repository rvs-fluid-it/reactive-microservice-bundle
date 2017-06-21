package be.fluid_it.bootique.vertx.filter;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.rxjava.servicediscovery.types.MessageSource;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;

public class ChannelsDiscoverableFilter implements Handler<BridgeEvent> {
    private final Handler<BridgeEvent> handler;
    private final Vertx vertx;
    private final String serviceName;

    public ChannelsDiscoverableFilter(Handler<BridgeEvent> handler,
                                      Vertx vertx, String serviceName) {
        this.handler = handler;
        this.vertx = vertx;
        this.serviceName = serviceName;
    }

    @Override
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent.type() == BridgeEventType.REGISTER) {
            ServiceDiscovery discovery = ServiceDiscovery.create(vertx);
            JsonObject message = bridgeEvent.getRawMessage();
            String address = message.getString("address");

            Record record = MessageSource.createRecord(serviceName, address);
            discovery.publish(record, ar -> {
                if (ar.succeeded()) {
                    System.out.printf("Successfully registered channel [%s, %s] ...\n", serviceName, address);
                }
                discovery.close();
            });
        }
        handler.handle(bridgeEvent);
    }
}
