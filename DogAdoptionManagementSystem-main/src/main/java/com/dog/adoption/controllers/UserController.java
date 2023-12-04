package com.dog.adoption.controllers;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.dog.adoption.data.UserData;
import com.dog.adoption.models.User;
import com.dog.adoption.services.UserService;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String getHomePage() {return "home";}

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest",  new User());
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new User());
        return "login";
    }

    @GetMapping("/user_page")
    public String getUserPage() {return "user_page";}

    @PostMapping("/register")
    public String register(@ModelAttribute UserData request, @RequestParam String confirmPassword, Model model) {
        try {
            if (!request.getPassword().equals(confirmPassword)) {
                throw new RuntimeException("Passwords do not match");
            }
            User registeredUser = userService.register(request);
            return registeredUser == null ? "error" : "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", request);
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpSession session) {
        try {
            User authenticated = userService.authentication(user.getEmail(), user.getPassword());
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login_page";
        }
    }

}
