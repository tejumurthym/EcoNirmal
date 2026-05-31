package com.econirmal.reporting.controller;

import com.econirmal.reporting.dto.ReportForWorkerDTO;
import com.econirmal.reporting.dto.VerifyRequest;
import com.econirmal.reporting.dto.RejectRequest;
import com.econirmal.reporting.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired private ReportService reportService;
    private final String workerEmail = "worker@example.com"; // change to actual worker email

    @GetMapping("/reports/pending")
    public ResponseEntity<List<ReportForWorkerDTO>> getPendingReports() {
        return ResponseEntity.ok(reportService.getPendingReports());
    }

    @PutMapping("/reports/{id}/verify")
    public ResponseEntity<String> verifyReport(@PathVariable Long id, @RequestBody VerifyRequest request) {
        try {
            return ResponseEntity.ok(reportService.verifyReport(id, request, workerEmail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reports/{id}/reject")
    public ResponseEntity<String> rejectReport(@PathVariable Long id, @RequestBody RejectRequest request) {
        try {
            return ResponseEntity.ok(reportService.rejectReport(id, request, workerEmail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reports/verified")
    public ResponseEntity<List<ReportForWorkerDTO>> getVerifiedReports() {
        return ResponseEntity.ok(reportService.getVerifiedReports());
    }
}