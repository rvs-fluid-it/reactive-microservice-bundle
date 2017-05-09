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
        Vertx vertx = null;
        try {
            vertx = vertxProvider.get();
        }  catch (Exception e) {
            return CommandOutcome.failed(1, e);
        }

        try {
            Thread.currentThread().join();
        } catch (InterruptedException ie) {

            // interruption of a running Vertx daemon is a normal event, so unless we get shutdown errors, return success
            try {
                if (vertx != null) {
                    vertx.close();
                }
            } catch (Exception se) {
                return CommandOutcome.failed(1, se);
            }
        }
        return CommandOutcome.succeeded();
    }
}
