package com.roxane.workload_planner.controller;

import com.roxane.workload_planner.model.User;
import com.roxane.workload_planner.repository.UserRepository;
import com.roxane.workload_planner.service.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService,
                             UserRepository userRepository) {
        super(userRepository);
        this.profileService = profileService;
    }

    @GetMapping
    public String showProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("displayName", getDisplayName(authentication));
        return "profile/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@RequestParam String newUsername,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String jobDescription,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {
        String currentUsername = authentication.getName();
        String error = profileService.updateProfile(
                currentUsername, newUsername, firstName, lastName, jobDescription);

        if (error != null) {
            redirectAttributes.addFlashAttribute("errorMessage", error);
            return "redirect:/profile";
        }

        redirectAttributes.addFlashAttribute("successMessage",
                "Profile updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/password")
    public String updatePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        String error = profileService.updatePassword(
                username, currentPassword, newPassword, confirmPassword);

        if (error != null) {
            redirectAttributes.addFlashAttribute("errorMessage", error);
            return "redirect:/profile";
        }

        redirectAttributes.addFlashAttribute("successMessage",
                "Password updated successfully!");
        return "redirect:/profile";
    }
}