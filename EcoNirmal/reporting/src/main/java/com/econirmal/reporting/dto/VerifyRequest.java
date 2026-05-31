package com.econirmal.reporting.dto;

public class VerifyRequest {
    private String violatorName;
    private Double fineAmount;
    private String workerComment;

    // Getters and setters
    public String getViolatorName() { return violatorName; }
    public void setViolatorName(String violatorName) { this.violatorName = violatorName; }
    public Double getFineAmount() { return fineAmount; }
    public void setFineAmount(Double fineAmount) { this.fineAmount = fineAmount; }
    public String getWorkerComment() { return workerComment; }
    public void setWorkerComment(String workerComment) { this.workerComment = workerComment; }
}