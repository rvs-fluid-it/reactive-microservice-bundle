package be.fluid_it.bootique.vertx;

import be.fluid_it.bootique.vertx.verticles.RoutingHttpServerVerticle;
import com.google.inject.Binder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import io.bootique.ModuleExtender;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;

public class VertxModuleExtender extends ModuleExtender<VertxModuleExtender> {
    private Multibinder<Verticle> verticles;
    private MapBinder<Class, DeploymentOptions> deploymentOptionsMap;

    public VertxModuleExtender(Binder binder) {
        super(binder);
    }

    @Override
    public VertxModuleExtender initAllExtensions() {
        contributeVerticles();
        contributeDeploymentOptions();
        return null;
    }

    public VertxModuleExtender addVerticle(Class<? extends Verticle> verticleClazz) {
        contributeVerticles().addBinding().to(verticleClazz);
        return this;
    }

    public VertxModuleExtender addVerticle(Class<? extends Verticle> verticleClazz, DeploymentOptions deploymentOptions) {
        addVerticle(verticleClazz).addDeploymentOptions(verticleClazz, deploymentOptions);
        return this;
    }

    private VertxModuleExtender addDeploymentOptions(Class verticleClazz, DeploymentOptions deploymentOptions) {
        contributeDeploymentOptions().addBinding(verticleClazz).toInstance(deploymentOptions);
        return this;
    }

    private Multibinder<Verticle> contributeVerticles() {
        if (verticles == null) {
            verticles = newSet(Verticle.class);
            verticles.addBinding().to(RoutingHttpServerVerticle.class);
        }
        return verticles;
    }

    private MapBinder<Class, DeploymentOptions> contributeDeploymentOptions() {
        if (deploymentOptionsMap == null) {
            deploymentOptionsMap = newMap(Class.class , DeploymentOptions.class);
        }
        return deploymentOptionsMap;
    }

}
