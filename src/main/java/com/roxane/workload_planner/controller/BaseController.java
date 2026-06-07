package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.model.User;
import com.roxane.workload_planner.repository.UserRepository;
import org.springframework.security.core.Authentication;

public class BaseController {

    protected final UserRepository userRepository;

    public BaseController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected String getDisplayName(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        if (user.getFirstName() != null && !user.getFirstName().trim().isEmpty()
                && user.getLastName() != null && !user.getLastName().trim().isEmpty()) {
            return user.getFirstName() + " " + user.getLastName();
        }
        // Fall back to username if no name set
        return user.getUsername();
    }
}