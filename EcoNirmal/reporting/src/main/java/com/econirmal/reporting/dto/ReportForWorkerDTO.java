package com.econirmal.reporting.dto;

import java.time.LocalDateTime;

public class ReportForWorkerDTO {
    private Long id;
    private String description;
    private String imagePath;
    private Double latitude;
    private Double longitude;
    private String status;
    private LocalDateTime reportedAt;

    public ReportForWorkerDTO(Long id, String description, String imagePath, Double latitude, Double longitude, String status, LocalDateTime reportedAt) {
        this.id = id;
        this.description = description;
        this.imagePath = imagePath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.reportedAt = reportedAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public String getStatus() { return status; }
    public LocalDateTime getReportedAt() { return reportedAt; }
}