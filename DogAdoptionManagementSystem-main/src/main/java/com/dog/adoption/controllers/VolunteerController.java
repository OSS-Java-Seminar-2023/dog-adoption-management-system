package com.dog.adoption.controllers;

import com.dog.adoption.models.*;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.repository.UserRepository;
import com.dog.adoption.repository.VolunteerRepository;
import com.dog.adoption.services.VolunteerService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class VolunteerController {
    private final VolunteerService volunteerService;
    private final DogRepository dogRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @GetMapping("/user_page/user_volunteer")
    public String getUserVolunteerPage(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "user_volunteer";
    }

    @PostMapping("/user_page/user_volunteer/volunteer-options")
    public String volunteerForJob(@ModelAttribute Volunteer volunteer, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (volunteer.getDog() != null) {
            Dog selectedDog = dogRepository.findById(volunteer.getDog().getId()).orElse(null);
            volunteer.setDog(selectedDog);
        }

        volunteer.setUser(loggedInUser);

        volunteerService.saveVolunteer(volunteer);
        return "redirect:/user_page/user_volunteer";
    }

}
