package com.example.keycloak.dtos.request;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class UserCreateRequest {
    // Basic Info
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // UI usually asks for this
    
    // Status
    private Boolean enabled = true;
    private Boolean emailVerified = false;

    private String orgId;

    // Custom data (like your organization_id)
    private Map<String, List<String>> attributes;

    // Optional: Pre-assigning groups or actions
    private List<String> groups;
    private List<String> requiredActions; // e.g., "UPDATE_PASSWORD"
}