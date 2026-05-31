package com.econirmal.reporting.service;

import com.econirmal.reporting.dto.UserInfoDTO;
import com.econirmal.reporting.dto.ReportWithCitizenDTO;
import com.econirmal.reporting.entity.User;
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
        return userRepository.findByRoleAndApproved("WORKER", false).stream()
            .map(u -> new UserInfoDTO(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.isApproved(), u.getContributionPoints(), u.getCreatedAt()))
            .collect(Collectors.toList());
    }

    public String approveWorker(Long workerId) throws Exception {
        User worker = userRepository.findById(workerId).orElseThrow(() -> new Exception("Worker not found"));
        if (!worker.getRole().equals("WORKER")) throw new Exception("User is not a worker");
        worker.setApproved(true);
        userRepository.save(worker);
        return "Worker approved successfully";
    }

    public List<UserInfoDTO> getAllCitizens() {
        return userRepository.findByRole("CITIZEN").stream()
            .map(u -> new UserInfoDTO(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.isApproved(), u.getContributionPoints(), u.getCreatedAt()))
            .collect(Collectors.toList());
    }

    public List<ReportWithCitizenDTO> getAllReports() {
        return reportRepository.findAllByOrderByReportedAtDesc().stream()
            .map(r -> new ReportWithCitizenDTO(r.getId(), r.getCitizen().getName(), r.getCitizen().getEmail(), r.getDescription(), r.getImagePath(), r.getLatitude(), r.getLongitude(), r.getStatus(), r.getViolatorName(), r.getFineAmount(), r.isFinePaid(), r.getReportedAt()))
            .collect(Collectors.toList());
    }
}
