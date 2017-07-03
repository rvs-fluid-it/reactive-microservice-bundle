package be.fluid_it.bootique.vertx.config;

public class PermissionsConfig {
    private String[] inbound = new String[]{};
    private String[] outbound = new String[]{};
    private String[] inboundRegex = new String[]{};
    private String[] outboundRegex = new String[]{};

    public PermissionsConfig() {
    }
    public PermissionsConfig(String dummy) {
    }

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

    public String[] inboundRegex() {
        return inboundRegex;
    }

    public void setInboundRegex(String[] inboundRegex) {
        this.inboundRegex = inboundRegex;
    }

    public String[] outboundRegex() {
        return outboundRegex;
    }

    public void setOutboundRegex(String[] outboundRegex) {
        this.outboundRegex = outboundRegex;
    }
}
