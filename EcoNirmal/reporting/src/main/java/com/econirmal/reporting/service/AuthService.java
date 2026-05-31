package com.econirmal.reporting.service;

import com.econirmal.reporting.dto.LoginRequest;
import com.econirmal.reporting.dto.LoginResponse;
import com.econirmal.reporting.dto.RegisterRequest;
import com.econirmal.reporting.entity.User;
import com.econirmal.reporting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already registered!";
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toUpperCase());
        if (user.getRole().equals("WORKER")) {
            user.setApproved(false);
        } else {
            user.setApproved(true);
        }
        user.setContributionPoints(0);
        userRepository.save(user);
        return "User registered successfully!";
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        
        // Case 1: User not found
        if (optionalUser.isEmpty()) {
            return new LoginResponse("Invalid email or password", null, null, null, null);
        }
        
        User user = optionalUser.get();
        
        // Case 2: Password incorrect
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse("Invalid email or password", null, null, null, null);
        }
        
        // Case 3: Worker not approved
        if (user.getRole().equals("WORKER") && !user.isApproved()) {
            return new LoginResponse("Your worker account is pending admin approval", null, null, null, null);
        }
        
        // Case 4: Login successful
        return new LoginResponse("Login successful", user.getRole(), user.getName(), user.getId(), user.getEmail());
    }
}