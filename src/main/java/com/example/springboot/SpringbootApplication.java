package com.example.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // redis chaching
public class SpringbootApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "/home/ubuntu/application-real.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringbootApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
//        SpringApplication.run(SpringbootApplication.class, args);
    }
}
