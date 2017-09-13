package be.fluid_it.bootique.vertx.filter;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.rxjava.servicediscovery.types.MessageSource;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;

public class ChannelsDiscoverableFilter implements Handler<BridgeEvent> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

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
        switch (bridgeEvent.type()) {
            case REGISTER:
                {
                    ServiceDiscovery discovery = ServiceDiscovery.create(vertx);
                    JsonObject message = bridgeEvent.getRawMessage();
                    String address = message.getString("address");

                    Record record = MessageSource.createRecord(serviceName, address);
                    discovery.publish(record, ar -> {
                        if (ar.succeeded()) {
                            System.out.printf("Successfully registered channel [%s, %s] ...\n", serviceName, address);
                        }
                    });
                    discovery.close();
                }
                break;
            case SOCKET_CLOSED:
            case UNREGISTER:
                {
                    ServiceDiscovery discovery = ServiceDiscovery.create(vertx);
                    JsonObject message = bridgeEvent.getRawMessage();
                    String address = message.getString("address");

                    Record record = MessageSource.createRecord(serviceName, address);
                    discovery.unpublish(record.getRegistration(), ar -> {
                        if (ar.succeeded()) {
                            logger.info("Successfully unregistered channel [%s, %s] ...\n", serviceName, address);
                        }
                    });
                    discovery.close();
                }
                break;
            default:
        }
        handler.handle(bridgeEvent);
    }
}
