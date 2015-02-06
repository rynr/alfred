package org.rjung.alfred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "org.rjung.alfred")
@EnableAutoConfiguration
@Configuration
public class Service {

    public static void main(String[] args) {
        SpringApplication.run(Service.class, args);
    }

}