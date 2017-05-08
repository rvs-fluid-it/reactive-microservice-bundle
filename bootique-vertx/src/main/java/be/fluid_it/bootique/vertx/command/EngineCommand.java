package be.fluid_it.bootique.vertx.command;

import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;
import io.vertx.core.Vertx;

import javax.inject.Inject;
import javax.inject.Provider;

public class EngineCommand  extends CommandWithMetadata {
    private Provider<Vertx> vertxProvider;

    @Inject
    public EngineCommand(Provider<Vertx> vertxProvider) {
        super(createMetadata());
        this.vertxProvider = vertxProvider;
    }


    private static CommandMetadata createMetadata() {
        return CommandMetadata.builder(EngineCommand.class).description("Starts Vertx engine.").build();
    }

    @Override
    public CommandOutcome run(Cli cli) {
        Vertx vertx = vertxProvider.get();
        return CommandOutcome.succeeded();
    }
}
