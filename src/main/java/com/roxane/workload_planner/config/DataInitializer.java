package com.roxane.workload_planner.config;

import com.roxane.workload_planner.model.User;
import com.roxane.workload_planner.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Only create users if the database is empty
        if (userRepository.count() == 0) {
            User manager = new User();
            manager.setUsername("manager");
            manager.setPassword(passwordEncoder.encode("manager123"));
            manager.setRole("MANAGER");
            manager.setEmail("manager@example.com");
            userRepository.save(manager);

            User member = new User();
            member.setUsername("member");
            member.setPassword(passwordEncoder.encode("member123"));
            member.setRole("MEMBER");
            member.setEmail("member@example.com");
            userRepository.save(member);

            System.out.println("Test users created!");
        }
    }
}