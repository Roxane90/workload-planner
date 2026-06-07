package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    // Show register page
    @GetMapping
    public String showRegisterPage() {
        return "auth/register";
    }

    // Handle register form submission
    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           Model model) {

        String error = registerService.register(
                username, password, confirmPassword, firstName, lastName);

        if (error != null) {
            // Something went wrong — show error on register page
            model.addAttribute("errorMessage", error);
            return "auth/register";
        }

        // Success — redirect to login with success message
        return "redirect:/login?registered";
    }
}