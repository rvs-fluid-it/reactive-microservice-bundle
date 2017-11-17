package be.fluid_it.rµs.bundle.minimal.showcase.app;

import be.fluid_it.rµs.bundle.minimal.showcase.modules.ShowcaseModule;
import io.bootique.Bootique;

public class CounterRunner {
    public static void main(String[] args) {
        Bootique.app(new String[]{"--engine", "--config=classpath:counter.yml"}).autoLoadModules().module(ShowcaseModule.class).run();
    }
}
