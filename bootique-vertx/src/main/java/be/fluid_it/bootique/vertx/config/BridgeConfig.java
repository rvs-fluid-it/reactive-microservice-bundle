package be.fluid_it.bootique.vertx.config;

public class BridgeConfig {
    private PermissionsConfig permissions = new PermissionsConfig();

    public PermissionsConfig permissions() {
        return permissions;
    }

    public void setPermissions(PermissionsConfig permissions) {
        this.permissions = permissions;
    }
}
