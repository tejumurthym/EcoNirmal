package com.econirmal.reporting.controller;

import com.econirmal.reporting.dto.UserInfoDTO;
import com.econirmal.reporting.dto.ReportWithCitizenDTO;
import com.econirmal.reporting.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired private AdminService adminService;

    @GetMapping("/pending-workers")
    public ResponseEntity<List<UserInfoDTO>> getPendingWorkers() {
        return ResponseEntity.ok(adminService.getPendingWorkers());
    }

    @PutMapping("/approve-worker/{id}")
    public ResponseEntity<String> approveWorker(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.approveWorker(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/citizens")
    public ResponseEntity<List<UserInfoDTO>> getAllCitizens() {
        return ResponseEntity.ok(adminService.getAllCitizens());
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportWithCitizenDTO>> getAllReports() {
        return ResponseEntity.ok(adminService.getAllReports());
    }
}
