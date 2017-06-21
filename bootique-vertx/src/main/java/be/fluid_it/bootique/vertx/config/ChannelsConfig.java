package be.fluid_it.bootique.vertx.config;

public class ChannelsConfig {
    private boolean discoverable = false;

    private String serviceName = "";

    public boolean isDiscoverable() {
        return discoverable;
    }

    public void setDiscoverable(boolean discoverable) {
        this.discoverable = discoverable;
    }

    public String serviceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
