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

            User member1 = new User();
            member1.setUsername("member1");
            member1.setPassword(passwordEncoder.encode("member123"));
            member1.setRole("MEMBER");
            member1.setEmail("member1@example.com");
            userRepository.save(member1);

            User member2 = new User();
            member2.setUsername("member2");
            member2.setPassword(passwordEncoder.encode("member123"));
            member2.setRole("MEMBER");
            member2.setEmail("member2@example.com");
            userRepository.save(member2);

            System.out.println("Test users created!");
        }
    }
}