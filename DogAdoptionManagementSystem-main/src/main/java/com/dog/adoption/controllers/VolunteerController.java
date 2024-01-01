package com.dog.adoption.controllers;

import com.dog.adoption.models.*;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.repository.VolunteerRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.VolunteerService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class VolunteerController {
    private final VolunteerService volunteerService;
    private final DogRepository dogRepository;
    private final DogService dogService;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @GetMapping("/user_page/user_volunteer")
    public String getUserVolunteerPage(
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "breed", required = false) String breed,
            @RequestParam(name = "ageCategory", required = false) String ageCategory,
            @RequestParam(name = "castrated", required = false, defaultValue = "false") boolean castrated,
            @RequestParam(name = "sterilised", required = false, defaultValue = "false") boolean sterilised,
            @RequestParam(name = "gender", required = false) String gender,
            Model model) {

        List<Dog> dogs = dogService.listAllDogs(size, breed, ageCategory, castrated, sterilised, gender);

        model.addAttribute("dogs", dogs);
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

    @GetMapping("/staff_page/staff_manage_volunteers")
    public String filterVolunteersByDay(
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "breed", required = false) String breed,
            @RequestParam(name = "ageCategory", required = false) String ageCategory,
            @RequestParam(name = "castrated", required = false, defaultValue = "false") boolean castrated,
            @RequestParam(name = "sterilised", required = false, defaultValue = "false") boolean sterilised,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam(required = false) String job,
            @RequestParam(required = false) UUID dogId,
            Model model
    ) {
        List<Volunteer> filteredVolunteers;
        List<Dog> dogs;

        if (dogId != null) {
            filteredVolunteers = volunteerService.getVolunteersByDogId(dogId);
        } else if (start != null && end != null) {
            filteredVolunteers = volunteerService.getVolunteersByTimePeriodOrBetweenStartStopTime(start, end, start, end);
        } else if (start != null) {
            filteredVolunteers = volunteerService.getVolunteersFromStartTime(start);
        } else if (job != null && !job.isEmpty()) {
            filteredVolunteers = volunteerService.getVolunteersByJob(job);
        } else {
            filteredVolunteers = volunteerService.getAllVolunteers();
        }

        dogs = dogService.listAllDogs(size, breed, ageCategory, castrated, sterilised, gender);

        model.addAttribute("filteredVolunteers", filteredVolunteers);
        model.addAttribute("dogs", dogs);

        return "staff_manage_volunteers";
    }


}

