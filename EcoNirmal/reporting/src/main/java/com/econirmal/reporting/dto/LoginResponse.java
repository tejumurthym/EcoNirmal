package com.econirmal.reporting.dto;

public class LoginResponse {
    private String message;
    private String role;
    private String name;
    private Long id;
    private String email;          // <-- ADD THIS

    // Updated constructor
    public LoginResponse(String message, String role, String name, Long id, String email) {
        this.message = message;
        this.role = role;
        this.name = name;
        this.id = id;
        this.email = email;
    }

    // Getters
    public String getMessage() { return message; }
    public String getRole() { return role; }
    public String getName() { return name; }
    public Long getId() { return id; }
    public String getEmail() { return email; }   // <-- ADD THIS
}