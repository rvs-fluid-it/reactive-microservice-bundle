package be.fluid_it.bootique.vertx.filter;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.rxjava.servicediscovery.types.MessageSource;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;

import java.util.HashMap;
import java.util.Map;

public class ChannelsDiscoverableFilter implements Handler<BridgeEvent> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Handler<BridgeEvent> handler;
    private final Vertx vertx;
    private final String serviceName;
    private final Map<String, String> registrations = new HashMap<>();

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
                            logger.info("Successfully registered channel [" +
                                    serviceName +
                                    ", " +
                                    address +
                                    "] by id [" +
                                    ar.result().getRegistration() +
                                    "]...\n");
                            this.registrations.put(bridgeEvent.socket().writeHandlerID(), ar.result().getRegistration());
                        }
                    });
                    discovery.close();
                }
                break;
            case SOCKET_CLOSED:
            case UNREGISTER:
                {
                    ServiceDiscovery discovery = ServiceDiscovery.create(vertx);
                    String writeHandlerID = bridgeEvent.socket().writeHandlerID();

                    if (registrations.containsKey(writeHandlerID)) {
                        discovery.unpublish(registrations.get(writeHandlerID), ar -> {
                            if (ar.succeeded()) {
                                logger.info("Successfully unregistered channel with id [" +
                                        writeHandlerID +
                                        "] ...\n");
                            }
                        });
                    }
                    discovery.close();
                }
                break;
            default:
        }
        handler.handle(bridgeEvent);
    }
}
