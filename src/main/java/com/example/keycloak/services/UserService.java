package com.example.keycloak.services;

import com.example.keycloak.dtos.request.UserCreateRequest;

public interface UserService {
    String createUser(UserCreateRequest request);
}