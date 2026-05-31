package com.econirmal.reporting.dto;

public class LoginResponse {
    private String message;
    private String role;
    private String name;
    private Long id;

    // Constructor
    public LoginResponse(String message, String role, String name, Long id) {
        this.message = message;
        this.role = role;
        this.name = name;
        this.id = id;
    }

    // Getters (no setters needed for response)
    public String getMessage() { return message; }
    public String getRole() { return role; }
    public String getName() { return name; }
    public Long getId() { return id; }
}