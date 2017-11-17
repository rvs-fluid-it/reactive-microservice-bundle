package be.fluid_it.rµs.bundle.minimal.showcase.services;

import be.fluid_it.rµs.bundle.minimal.showcase.domain.CounterService;
import io.vertx.core.json.JsonObject;

public class SimpleCounterService implements CounterService{
    private int total = 0;

    @Override
    public void handleEvent(JsonObject event) {
        switch(event.getString("type")) {
            case "[Counter] Increment":
                total++;
                break;
            case "[Counter] Decrement":
                total--;
                break;
            case "[Counter] Reset":
                total = 0;
                break;
        }
    }

    @Override
    public int total() {
        return total;
    }
}
