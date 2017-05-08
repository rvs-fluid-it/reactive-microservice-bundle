package be.fluid_it.rµs.bundle.showcase.domain;

import io.vertx.core.json.JsonObject;

public interface CounterService {
    void handleEvent(JsonObject event);
    int total();
}
