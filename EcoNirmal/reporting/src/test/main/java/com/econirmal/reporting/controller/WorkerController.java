package com.econirmal.reporting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.econirmal.reporting.dto.ReportForWorkerDTO;
import com.econirmal.reporting.dto.VerifyRequest;
import com.econirmal.reporting.dto.RejectRequest;
import com.econirmal.reporting.service.ReportService;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    private ReportService reportService;

    private final String workerEmail = "worker@example.com"; // Change to your worker email

    @GetMapping("/reports/pending")
    public ResponseEntity<List<ReportForWorkerDTO>> getPendingReports() {
        return ResponseEntity.ok(reportService.getPendingReports());
    }

    @PutMapping("/reports/{id}/verify")
    public ResponseEntity<String> verifyReport(@PathVariable Long id, @RequestBody VerifyRequest request) {
        try {
            String result = reportService.verifyReport(id, request, workerEmail);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reports/{id}/reject")
    public ResponseEntity<String> rejectReport(@PathVariable Long id, @RequestBody RejectRequest request) {
        try {
            String result = reportService.rejectReport(id, request, workerEmail);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reports/verified")
    public ResponseEntity<List<ReportForWorkerDTO>> getVerifiedReports() {
        return ResponseEntity.ok(reportService.getVerifiedReports());
    }
}