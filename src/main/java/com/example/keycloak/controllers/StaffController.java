package com.example.keycloak.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    // Get profile by token
    @GetMapping("/profile")
    public Map<String, String> getStaffProfile(@AuthenticationPrincipal Jwt jwt) {
        List<String> paths = jwt.getClaimAsStringList("organization_path");
        String fullPath = (paths != null && !paths.isEmpty()) ? paths.get(0) : "/Unknown";

        // Logic to split "/Organizations/CompanyA/Engineering"
        String[] segments = fullPath.split("/");

        return Map.of(
                "username", jwt.getClaimAsString("preferred_username"),
                "company_id", jwt.getClaimAsString("company_id"),
                "company", segments.length > 2 ? segments[2] : "N/A",
                "department", segments.length > 3 ? segments[3] : "N/A");
    }

    // Restricting by Specific User
    @GetMapping("/{userId}")
    public String getUserData(@PathVariable String userId, @AuthenticationPrincipal Jwt jwt) {
        String currentUserId = jwt.getSubject();

        if (!currentUserId.equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only access your own data");
        }

        return "Data for " + jwt.getClaimAsString("name") + " (" + userId + ")";
    }

    // Specific permission
    @GetMapping("/sensitive-data")
    @PreAuthorize("hasRole('admin_read')")
    public String getSecret() {
        return "Top Secret Data";
    }
}