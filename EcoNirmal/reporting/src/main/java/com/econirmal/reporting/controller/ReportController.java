package com.econirmal.reporting.controller;

import com.econirmal.reporting.dto.ReportForWorkerDTO;
import com.econirmal.reporting.entity.Report;
import com.econirmal.reporting.entity.User;
import com.econirmal.reporting.repository.ReportRepository;
import com.econirmal.reporting.repository.UserRepository;
import com.econirmal.reporting.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @PostMapping(value = "/submit", consumes = {"multipart/form-data"})
    public ResponseEntity<String> submitReport(
            @RequestParam String description,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam MultipartFile image) {
        try {
            String citizenEmail = "rajesh@example.com"; // change to dynamic later
            String result = reportService.submitReport(description, latitude, longitude, image, citizenEmail);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<ReportForWorkerDTO>> getMyReports(@RequestParam String email) {
        try {
            User citizen = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            List<Report> reports = reportRepository.findByCitizen(citizen);
            List<ReportForWorkerDTO> dtos = reports.stream()
                    .map(r -> new ReportForWorkerDTO(
                            r.getId(), r.getDescription(), r.getImagePath(),
                            r.getLatitude(), r.getLongitude(), r.getStatus(), r.getReportedAt()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}