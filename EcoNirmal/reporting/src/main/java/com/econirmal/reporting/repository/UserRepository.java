package com.econirmal.reporting.repository;

import com.econirmal.reporting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRoleAndApproved(String role, boolean approved);
    List<User> findByRole(String role);
}