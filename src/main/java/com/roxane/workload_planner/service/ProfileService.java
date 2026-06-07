package com.roxane.workload_planner.service;

import com.roxane.workload_planner.model.User;
import com.roxane.workload_planner.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String updateProfile(String currentUsername, String newUsername,
                                String firstName, String lastName,
                                String jobDescription) {

        User user = userRepository.findByUsername(currentUsername);

        // Check if new username is taken by someone else
        if (!newUsername.equals(currentUsername)) {
            if (userRepository.findByUsername(newUsername) != null) {
                return "Username already taken!";
            }
        }

        if (newUsername.trim().isEmpty()) {
            return "Username cannot be empty!";
        }

        user.setUsername(newUsername);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setJobDescription(jobDescription);

        userRepository.save(user);
        return null; // null means success
    }

    public String updatePassword(String username, String currentPassword,
                                 String newPassword, String confirmPassword) {

        User user = userRepository.findByUsername(username);

        // Check current password is correct
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "Current password is incorrect!";
        }

        // Check new passwords match
        if (!newPassword.equals(confirmPassword)) {
            return "New passwords do not match!";
        }

        // Check password length
        if (newPassword.length() < 6) {
            return "Password must be at least 6 characters!";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return null;
    }
}