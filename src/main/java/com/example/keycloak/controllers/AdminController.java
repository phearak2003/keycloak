package com.example.keycloak.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.keycloak.dtos.request.UserCreateRequest;
import com.example.keycloak.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello Swagger from Admin!";
    }

    @PostMapping("/users")
    public String createUser(@Valid @RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }
}
