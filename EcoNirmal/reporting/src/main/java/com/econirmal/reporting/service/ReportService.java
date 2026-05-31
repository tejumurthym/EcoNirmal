package com.econirmal.reporting.service;

import com.econirmal.reporting.dto.ReportForWorkerDTO;
import com.econirmal.reporting.dto.VerifyRequest;
import com.econirmal.reporting.dto.RejectRequest;
import com.econirmal.reporting.entity.Report;
import com.econirmal.reporting.entity.User;
import com.econirmal.reporting.repository.ReportRepository;
import com.econirmal.reporting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    private final String uploadDir = "./uploads/";

    public String submitReport(String description, Double latitude, Double longitude, MultipartFile image, String citizenEmail) throws IOException {
        User citizen = userRepository.findByEmail(citizenEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get(uploadDir + imageName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, image.getBytes());

        Report report = new Report();
        report.setCitizen(citizen);
        report.setDescription(description);
        report.setLatitude(latitude);
        report.setLongitude(longitude);
        report.setImagePath(imageName);
        report.setStatus("PENDING");
        report.setFinePaid(false);
        reportRepository.save(report);
        return "Report submitted successfully! ID: " + report.getId();
    }

    public List<ReportForWorkerDTO> getPendingReports() {
        return reportRepository.findByStatus("PENDING").stream()
            .map(r -> new ReportForWorkerDTO(r.getId(), r.getDescription(), r.getImagePath(), r.getLatitude(), r.getLongitude(), r.getStatus(), r.getReportedAt()))
            .collect(Collectors.toList());
    }

    public String verifyReport(Long reportId, VerifyRequest request, String workerEmail) throws Exception {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new Exception("Report not found"));
        if (!report.getStatus().equals("PENDING")) throw new Exception("Report already processed");
        User worker = userRepository.findByEmail(workerEmail).orElseThrow(() -> new Exception("Worker not found"));
        report.setStatus("VERIFIED");
        report.setViolatorName(request.getViolatorName());
        report.setFineAmount(request.getFineAmount());
        report.setWorkerComment(request.getWorkerComment());
        report.setWorker(worker);
        report.setVerifiedAt(LocalDateTime.now());
        reportRepository.save(report);

        User citizen = report.getCitizen();
        citizen.setContributionPoints(citizen.getContributionPoints() + 10);
        userRepository.save(citizen);
        return "Report verified. Fine issued: ₹" + request.getFineAmount();
    }

    public String rejectReport(Long reportId, RejectRequest request, String workerEmail) throws Exception {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new Exception("Report not found"));
        if (!report.getStatus().equals("PENDING")) throw new Exception("Report already processed");
        User worker = userRepository.findByEmail(workerEmail).orElseThrow(() -> new Exception("Worker not found"));
        report.setStatus("REJECTED");
        report.setWorkerComment(request.getWorkerComment());
        report.setWorker(worker);
        report.setVerifiedAt(LocalDateTime.now());
        reportRepository.save(report);
        return "Report rejected.";
    }

    public List<ReportForWorkerDTO> getVerifiedReports() {
        return reportRepository.findByStatus("VERIFIED").stream()
            .map(r -> new ReportForWorkerDTO(r.getId(), r.getDescription(), r.getImagePath(), r.getLatitude(), r.getLongitude(), r.getStatus(), r.getReportedAt()))
            .collect(Collectors.toList());
    }
}