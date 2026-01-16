package com.example.keycloak.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.keycloak.dtos.request.UserCreateRequest;
import com.example.keycloak.services.UserService;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${keycloak.realm}")
    private String realmName;

    private final Keycloak keycloak;

    @Override
    public String createUser(UserCreateRequest request) {
        UserRepresentation user = new UserRepresentation();
        BeanUtils.copyProperties(request, user);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(true);
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm(realmName).users().create(user);

        if (response.getStatus() == 201) {
            return "User created successfully!";
        } else {
            return "Error: " + response.getStatusInfo();
        }
    }
}