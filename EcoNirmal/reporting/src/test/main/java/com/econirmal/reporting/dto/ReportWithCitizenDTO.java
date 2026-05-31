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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCitizenName() {
		return citizenName;
	}

	public void setCitizenName(String citizenName) {
		this.citizenName = citizenName;
	}

	public String getCitizenEmail() {
		return citizenEmail;
	}

	public void setCitizenEmail(String citizenEmail) {
		this.citizenEmail = citizenEmail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getViolatorName() {
		return violatorName;
	}

	public void setViolatorName(String violatorName) {
		this.violatorName = violatorName;
	}

	public Double getFineAmount() {
		return fineAmount;
	}

	public void setFineAmount(Double fineAmount) {
		this.fineAmount = fineAmount;
	}

	public boolean isFinePaid() {
		return finePaid;
	}

	public void setFinePaid(boolean finePaid) {
		this.finePaid = finePaid;
	}

	public LocalDateTime getReportedAt() {
		return reportedAt;
	}

	public void setReportedAt(LocalDateTime reportedAt) {
		this.reportedAt = reportedAt;
	}
    
}