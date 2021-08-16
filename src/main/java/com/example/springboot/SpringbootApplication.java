package com.example.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching // redis chaching
@EnableJpaAuditing
@SpringBootApplication
public class SpringbootApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "optional:classpath:application-local.yml,"
            + "optional:/home/ubuntu/application-deploy.yml,"
            + "optional:/home/ubuntu/deploy/application-deploy.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringbootApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
