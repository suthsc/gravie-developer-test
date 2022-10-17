package net.suthsc.game.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "net.suthsc.site")
public class GravieDeveloperTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GravieDeveloperTestApplication.class, args);
    }

}
