package com.econirmal.reporting.service;

import com.econirmal.reporting.dto.UserInfoDTO;
import com.econirmal.reporting.dto.ReportWithCitizenDTO;
import com.econirmal.reporting.entity.User;
import com.econirmal.reporting.entity.Report;
import com.econirmal.reporting.repository.UserRepository;
import com.econirmal.reporting.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    public List<UserInfoDTO> getPendingWorkers() {
        List<User> pendingWorkers = userRepository.findByRoleAndApproved("WORKER", false);
        return pendingWorkers.stream()
            .map(user -> new UserInfoDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.isApproved(), user.getContributionPoints(), user.getCreatedAt()))
            .collect(Collectors.toList());
    }

    public String approveWorker(Long workerId) throws Exception {
        User worker = userRepository.findById(workerId)
            .orElseThrow(() -> new Exception("Worker not found"));
        if (!worker.getRole().equals("WORKER")) {
            throw new Exception("User is not a worker");
        }
        worker.setApproved(true);
        userRepository.save(worker);
        return "Worker approved successfully";
    }

    public List<UserInfoDTO> getAllCitizens() {
        List<User> citizens = userRepository.findByRole("CITIZEN");
        return citizens.stream()
            .map(user -> new UserInfoDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.isApproved(), user.getContributionPoints(), user.getCreatedAt()))
            .collect(Collectors.toList());
    }

    public List<ReportWithCitizenDTO> getAllReports() {
        List<Report> reports = reportRepository.findAllByOrderByReportedAtDesc();
        return reports.stream()
            .map(report -> new ReportWithCitizenDTO(
                report.getId(),
                report.getCitizen().getName(),
                report.getCitizen().getEmail(),
                report.getDescription(),
                report.getImagePath(),
                report.getLatitude(),
                report.getLongitude(),
                report.getStatus(),
                report.getViolatorName(),
                report.getFineAmount(),
                report.isFinePaid(),
                report.getReportedAt()
            ))
            .collect(Collectors.toList());
    }
}