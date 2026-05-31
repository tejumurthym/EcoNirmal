package com.econirmal.reporting.dto;

import java.time.LocalDateTime;

public class UserInfoDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private boolean approved;
    private int contributionPoints;
    private LocalDateTime createdAt;

    public UserInfoDTO(Long id, String name, String email, String role, boolean approved, int contributionPoints, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.approved = approved;
        this.contributionPoints = contributionPoints;
        this.createdAt = createdAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isApproved() { return approved; }
    public int getContributionPoints() { return contributionPoints; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}