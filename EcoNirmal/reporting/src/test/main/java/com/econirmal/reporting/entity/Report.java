package com.econirmal.reporting.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private User citizen;

    private String description;
    private String imagePath;
    private String videoPath;
    private Double latitude;
    private Double longitude;
    private String status;
    private String violatorName;
    private Double fineAmount;
    private boolean finePaid;
    private String workerComment;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private User worker;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getCitizen() {
		return citizen;
	}
	public void setCitizen(User citizen) {
		this.citizen = citizen;
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
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
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
	public String getWorkerComment() {
		return workerComment;
	}
	public void setWorkerComment(String workerComment) {
		this.workerComment = workerComment;
	}
	public User getWorker() {
		return worker;
	}
	public void setWorker(User worker) {
		this.worker = worker;
	}
	public LocalDateTime getReportedAt() {
		return reportedAt;
	}
	public void setReportedAt(LocalDateTime reportedAt) {
		this.reportedAt = reportedAt;
	}
	public LocalDateTime getVerifiedAt() {
		return verifiedAt;
	}
	public void setVerifiedAt(LocalDateTime verifiedAt) {
		this.verifiedAt = verifiedAt;
	}
	private LocalDateTime reportedAt = LocalDateTime.now();
    private LocalDateTime verifiedAt;

    
    
}