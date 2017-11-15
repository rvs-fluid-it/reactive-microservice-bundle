package be.fluid_it.rµs.bundle.minimal.showcase.app;

import be.fluid_it.rµs.bundle.minimal.showcase.modules.HelloModule;
import io.bootique.Bootique;

public class HelloWorldApp {
    public static void main(String[] args) {
        Bootique.app(new String[]{"--engine", "--config=classpath:hello.yml"}).autoLoadModules().module(HelloModule.class).run();
    }
}
