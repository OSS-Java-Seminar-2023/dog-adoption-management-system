package com.dog.adoption.controllers;

import com.dog.adoption.data.UserData;
import com.dog.adoption.models.User;
import com.dog.adoption.repository.UserRepository;
import com.dog.adoption.services.UserService;
import com.dog.adoption.utils.PasswordUtils;
import com.dog.adoption.utils.ValidationUtils;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

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
            request.setRole("User");
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

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact";
    }

    @GetMapping("/user_my_profile")
    public String getMyProfile(Model model, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            return "user_my_profile";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/update-user-profile")
    public String updateProfile(@ModelAttribute User updatedUser, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getEmail())) {
                loggedInUser.setEmail(updatedUser.getEmail());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getPhoneNumber())) {
                loggedInUser.setPhoneNumber(updatedUser.getPhoneNumber());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getCity())) {
                loggedInUser.setCity(updatedUser.getCity());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getAddress())) {
                loggedInUser.setAddress(updatedUser.getAddress());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getPassword())) {
                loggedInUser.setPassword(PasswordUtils.hashPassword(updatedUser.getPassword()));
            }
            userRepository.save(loggedInUser);
        }
        return "redirect:/user_my_profile";
    }

    @GetMapping("/staff_page")
    public String getStaffPage(Model model, HttpSession session) {
        User staff = (User)session.getAttribute("loggedInUser");
        model.addAttribute("staff", staff);
        return "staff_page";
    }

    @GetMapping("/staff_my_profile")
    public String getStaffProfile(Model model, HttpSession session) {
        User user = (User)session.getAttribute("loggedInUser");

        if (user != null) {
            model.addAttribute("user", user);
            return "staff_my_profile";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/staff-update-profile")
    public String postStaffUpdatePage(@ModelAttribute User updatedUser, HttpSession session) {
        User user = (User)session.getAttribute("loggedInUser");

        if (user != null) {
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getPhoneNumber())) {
                user.setPhoneNumber(updatedUser.getPhoneNumber());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getCity())) {
                user.setCity(updatedUser.getCity());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getAddress())) {
                user.setAddress(updatedUser.getAddress());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getName())) {
                user.setName(updatedUser.getName());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getSurname())) {
                user.setSurname(updatedUser.getSurname());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getGender())) {
                user.setGender(updatedUser.getGender());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getPassword())) {
                user.setPassword(PasswordUtils.hashPassword(updatedUser.getPassword()));
            }
            userRepository.save(user);
        }
        return "redirect:/staff_my_profile";
    }

    @GetMapping("/admin_page")
    public String getAdminPage() {
        return "admin_page";
    }

    @GetMapping("/admin_manage_{type}_subject")
    public String getAdminList(@RequestParam(name = "type", defaultValue = "") String type, Model model){
        model.addAttribute("type", type);
        if ("manageStaff".equals(type)) {
            List<User> users = userService.listAllUsers("Staff");
            model.addAttribute("type", "manageStaff");
            model.addAttribute("users", users);

        } else if ("manageUser".equals(type)) {
            List<User> users = userService.listAllUsers("User");
            model.addAttribute("type", "manageUser");
            model.addAttribute("users", users);
        }
        else if ("manageAdmin".equals(type)) {
            List<User> users = userService.listAllUsers("Admin");
            model.addAttribute("type", "manageAdmin");
            model.addAttribute("users", users);
        }
        return "admin_manage_staff_user_admin";
    }
        @GetMapping("/admin_add")
        public String getAdminAddPage(Model model, @RequestParam(name = "type", defaultValue = "") String type) {
            User user = new User();

            if ("addStaff".equals(type)) {
                user.setRole("Staff");
            } else if ("addUser".equals(type)) {
                user.setRole("User");
            } else if ("addAdmin".equals(type)) {
                user.setRole("Admin");
            } else {
                return "error";
            }

            model.addAttribute("registerRequest", user);
            model.addAttribute("type", type);  // Add 'type' attribute for conditional logic in the template
            return "admin_add";
        }


    @PostMapping("/submit-add-{type}")
    public String postAdminAddPage(@ModelAttribute UserData request, @RequestParam String confirmPassword, Model model, HttpSession session,@RequestParam(name = "type", defaultValue = "") String type){
        try {
            if (!request.getPassword().equals(confirmPassword)) {;
                throw new RuntimeException("Passwords do not match");
            }

            if(type.equals("submitStaff")) {
                request.setRole("Staff");
                User newUser = userService.register(request);
                if (newUser == null) {
                    return "error";
                }
                return "redirect:/admin_manage_staff?type=manageStaff";
            }
            else if(type.equals("submitUser")) {
                request.setRole("User");
                User newUser = userService.register(request);
                if (newUser == null) {
                    return "error";
                }
                return "redirect:/admin_manage_user?type=manageUser";
            }
            else if(type.equals("submitAdmin")) {
                request.setRole("Admin");
                User newUser = userService.register(request);
                if (newUser == null) {
                    return "error";
                }
                return "redirect:/admin_manage_admin?type=manageAdmin";
            }
            return "error";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", request);
            if(type.equals("submitStaff")) {
                return "redirect:/admin_add?type=manageStaff";
            }
            else if (type.equals("submitUser")){
                return "redirect:/admin_add?type=manageUser";
            }
            else if (type.equals("submitAdmin")){
                return "redirect:/admin_add?type=manageAdmin";
            }
            return "error";
        }
    }

    @GetMapping("/admin_update")
    public String getAdminUpdatePage(@RequestParam("user_id") UUID userId, Model model, HttpSession session){
        User selectedUser = userService.getUserById(userId);
        model.addAttribute("user", selectedUser);
        if(selectedUser.getRole().equals("Staff")) {
            model.addAttribute("type","updateStaff");
        }
        else if(selectedUser.getRole().equals("User")){
            model.addAttribute("type","updateUser");
        }
        else if(selectedUser.getRole().equals("Admin")){
            model.addAttribute("type","updateAdmin");
        }
        session.setAttribute("selectedUser", selectedUser);
        return "admin_update_staff_user_admin";
    }

    @PostMapping("/admin-update-profile")
    public String postAdminUpdatePage(@ModelAttribute User updatedUser, HttpSession session) {
        User user = (User)session.getAttribute("selectedUser");

        if (user != null) {
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getEmail())) {
                user.setEmail(updatedUser.getEmail());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getPhoneNumber())) {
                user.setPhoneNumber(updatedUser.getPhoneNumber());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getCity())) {
                user.setCity(updatedUser.getCity());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getAddress())) {
                user.setAddress(updatedUser.getAddress());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getName())) {
                user.setName(updatedUser.getName());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getSurname())) {
                user.setSurname(updatedUser.getSurname());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getGender())) {
                user.setGender(updatedUser.getGender());
            }
            if (!ValidationUtils.isLongNullOrEmpty(updatedUser.getOib())) {
                user.setOib(updatedUser.getOib());
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getPassword())) {
                user.setPassword(PasswordUtils.hashPassword(updatedUser.getPassword()));
            }
            if (!ValidationUtils.isStringNullOrEmpty(updatedUser.getRole())) {
                user.setRole(PasswordUtils.hashPassword(updatedUser.getRole()));
            }
            userRepository.save(user);
        }
        session.removeAttribute("selectedUser");

        if(user.getRole().equals("Staff")){
            return "redirect:/admin_manage_staff_subject?type=manageStaff";
        }
        else if (user.getRole().equals("User")){
            return "redirect:/admin_manage_user_subject?type=manageUser";
        }
        else if(user.getRole().equals("Admin")){
            User loggedInUser = (User)session.getAttribute("loggedInUser");
            if(loggedInUser.getId().equals(user.getId())) {
                session.setAttribute("loggedInUser", user);
                return "redirect:/admin_page";
            }
            return "redirect:/admin_manage_admin_subject?type=manageAdmin";

        }
        return "error";
    }
    @GetMapping("/admin_delete")
    public String getAdminDeletePage(@RequestParam("user_id") UUID userId, Model model, HttpSession session){
        User selectedUser = userService.getUserById(userId);
        if (selectedUser.getRole().equals("Staff")){
            selectedUser.setRole("RemovedStaff");
        }
        else if(selectedUser.getRole().equals("User")){
            selectedUser.setRole("RemovedUser");
        }
        else if(selectedUser.getRole().equals("Admin")){
            selectedUser.setRole("RemovedAdmin");
        }
        userRepository.save(selectedUser);
        if(selectedUser.getRole().equals("RemovedStaff")){
            return "redirect:/admin_manage_staff_subject?type=manageStaff";
        }
        else if(selectedUser.getRole().equals("RemovedUser")){
            return "redirect:/admin_manage_user_subject?type=manageUser";
        }
        else if(selectedUser.getRole().equals("RemovedAdmin")){

            return "redirect:/admin_manage_admin_subject?type=manageAdmin";
        }
        return "error";
    }


}

