package com.dog.adoption.controllers;

import com.dog.adoption.repository.UserRepository;
import com.dog.adoption.utils.PasswordUtils;
import com.dog.adoption.utils.ValidationUtils;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.dog.adoption.data.UserData;
import com.dog.adoption.models.User;
import com.dog.adoption.services.UserService;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserData request, @RequestParam String confirmPassword, Model model, HttpSession session) {
        try {
            if (!request.getPassword().equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }
            User registeredUser = userService.register(request);
            if (registeredUser == null) {
                return "error";
            }
            session.setAttribute("loggedInUser", registeredUser);
            return "redirect:/user_page";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", request);
            return "register";
        }
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model,  @RequestParam String password, @RequestParam String email, HttpSession session) {
        try {
            user.setPassword(password);
            user.setEmail(email);
            User authenticatedUser = userService.authentication(user.getEmail(), user.getPassword());

            if (authenticatedUser != null) {
                session.setAttribute("loggedInUser", authenticatedUser);
                if (authenticatedUser.getRole().equals("Admin")) {
                    return "redirect:/admin_page";
                } else if (authenticatedUser.getRole().equals("Staff")) {
                    return "redirect:/staff_page";
                }
                return "redirect:/user_page";
            } else {
                throw new RuntimeException("Authentication failed");
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/user_page")
    public String getUserPage(Model model, HttpSession session) {
        User user = (User)session.getAttribute("loggedInUser");
        model.addAttribute("user", user);
        return "user_page";
    }

    @GetMapping("/user_page/user_my_profile")
    public String getMyProfile(Model model, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            return "user_my_profile";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/user_page/user_my_profile/update-profile")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            loggedInUser.setEmail(updatedUser.getEmail());
            loggedInUser.setPhoneNumber(updatedUser.getPhoneNumber());
            loggedInUser.setCity(updatedUser.getCity());
            loggedInUser.setAddress(updatedUser.getAddress());
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getPassword())) {
                loggedInUser.setPassword(PasswordUtils.hashPassword(updatedUser.getPassword()));
            }
            userRepository.save(loggedInUser);
        }
        return "redirect:/user_page/user_my_profile";
    }

    @GetMapping("/admin_page")
    public String getAdminPage() {
        return "admin_page";
    }

    @GetMapping("/admin_page/admin_manage_staff")
    public String getAdminStaffManagementPage() {
        return "admin_manage_staff";
    }

    @GetMapping("/staff_page")
    public String getStaffPage(Model model, HttpSession session) {
        User staff = (User)session.getAttribute("loggedInUser");
        model.addAttribute("staff", staff);
        return "staff_page";
    }

}