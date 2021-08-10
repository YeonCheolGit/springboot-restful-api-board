package com.example.springboot.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@Api(tags = {"7. profile"})
public class ProfileController {

    private final Environment environment;

    @GetMapping
    public String profile() {
        return Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("");
    }
}
