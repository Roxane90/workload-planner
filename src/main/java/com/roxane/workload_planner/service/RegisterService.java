package com.roxane.workload_planner.service;

import com.roxane.workload_planner.model.User;
import com.roxane.workload_planner.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Returns null if successful, error message if not
    public String register(String username, String password,
                           String confirmPassword, String firstName,
                           String lastName) {

        // Check passwords match
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match!";
        }

        // Check username not already taken
        if (userRepository.findByUsername(username) != null) {
            return "Username already taken!";
        }

        // Check username not empty
        if (username.trim().isEmpty()) {
            return "Username cannot be empty!";
        }

        // Check password length
        if (password.length() < 6) {
            return "Password must be at least 6 characters!";
        }

        // Create new user — always MEMBER role
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("MEMBER");
        user.setFirstName(firstName);
        user.setLastName(lastName);

        userRepository.save(user);
        return null; // null means success
    }
}