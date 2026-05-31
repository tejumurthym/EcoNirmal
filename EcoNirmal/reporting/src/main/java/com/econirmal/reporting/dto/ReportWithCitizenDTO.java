package com.econirmal.reporting.dto;

import java.time.LocalDateTime;

public class ReportWithCitizenDTO {
    private Long id;
    private String citizenName;
    private String citizenEmail;
    private String description;
    private String imagePath;
    private Double latitude;
    private Double longitude;
    private String status;
    private String violatorName;
    private Double fineAmount;
    private boolean finePaid;
    private LocalDateTime reportedAt;

    public ReportWithCitizenDTO(Long id, String citizenName, String citizenEmail, String description, String imagePath, Double latitude, Double longitude, String status, String violatorName, Double fineAmount, boolean finePaid, LocalDateTime reportedAt) {
        this.id = id;
        this.citizenName = citizenName;
        this.citizenEmail = citizenEmail;
        this.description = description;
        this.imagePath = imagePath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.violatorName = violatorName;
        this.fineAmount = fineAmount;
        this.finePaid = finePaid;
        this.reportedAt = reportedAt;
    }

    // Getters (generate)
    public Long getId() { return id; }
    public String getCitizenName() { return citizenName; }
    public String getCitizenEmail() { return citizenEmail; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getStatus() { return status; }
    public String getViolatorName() { return violatorName; }
    public Double getFineAmount() { return fineAmount; }
    public boolean isFinePaid() { return finePaid; }
    public LocalDateTime getReportedAt() { return reportedAt; }
}