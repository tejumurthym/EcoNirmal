package com.econirmal.reporting.repository;

import com.econirmal.reporting.entity.Report;
import com.econirmal.reporting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByCitizen(User citizen);
    List<Report> findByStatus(String status);
    List<Report> findAllByOrderByReportedAtDesc();
}