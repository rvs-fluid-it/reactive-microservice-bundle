package be.fluid_it.bootique.vertx.config;

public class PermissionsConfig {
    private String[] inbound = new String[]{};
    private String[] outbound = new String[]{};

    public String[] inbound() {
        return inbound;
    }

    public void setInbound(String[] inbound) {
        this.inbound = inbound;
    }

    public String[] outbound() {
        return outbound;
    }

    public void setOutbound(String[] outbound) {
        this.outbound = outbound;
    }
}
