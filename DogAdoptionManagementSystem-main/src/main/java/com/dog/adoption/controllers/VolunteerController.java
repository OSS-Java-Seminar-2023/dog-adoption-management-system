package com.dog.adoption.controllers;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.User;
import com.dog.adoption.models.Volunteer;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.repository.UserRepository;
import com.dog.adoption.repository.VolunteerRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.UserService;
import com.dog.adoption.services.VolunteerService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class VolunteerController {
    private final VolunteerService volunteerService;
    private final DogRepository dogRepository;
    private final DogService dogService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @GetMapping("/user_volunteer")
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

    @PostMapping("/volunteer-options")
    public String getVolunteerOptions(@ModelAttribute Volunteer volunteer, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");

        if (volunteer.getDog() != null) {
            Dog selectedDog = dogRepository.findById(volunteer.getDog().getId()).orElse(null);
            volunteer.setDog(selectedDog);
        }

        volunteer.setUser(loggedInUser);

        volunteerService.saveVolunteer(volunteer);
        return "redirect:/user_page";
    }

    @GetMapping("/{type}_manage_volunteers")
    public String getStaffAdminManageVolunteersPage(
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "breed", required = false) String breed,
            @RequestParam(name = "ageCategory", required = false) String ageCategory,
            @RequestParam(name = "castrated", required = false, defaultValue = "false") boolean castrated,
            @RequestParam(name = "sterilised", required = false, defaultValue = "false") boolean sterilised,
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "viewExecuted", defaultValue = "false") boolean viewExecuted,
            @RequestParam(name = "viewPending", defaultValue = "false") boolean viewPending,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestParam(required = false) String job,
            @RequestParam(required = false) UUID dogId,
            Model model, HttpSession session
    ) {
        List<Volunteer> filteredVolunteers;
        List<Dog> dogs;

        if (viewExecuted) {
            filteredVolunteers = volunteerService.getExecutedVolunteers();
        } else if (viewPending) {
            filteredVolunteers = volunteerService.getPendingVolunteers();
        } else if (dogId != null) {
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
        User loggedInUser =  (User)session.getAttribute("loggedInUser");
        model.addAttribute("filteredVolunteers", filteredVolunteers);
        model.addAttribute("dogs", dogs);
        model.addAttribute("type", loggedInUser.getRole());
        return "staff_admin_manage_volunteers";
    }

    @GetMapping("/{type}_update_volunteer")
    public String getStaffAdminUpdateVolunteerPage(@RequestParam("volunteerId") UUID volunteerId, Model model, HttpSession session){
        List<Dog> dogs = dogService.getAllDogs();
        List<User> users = userService.listAllUsers("User");
        Volunteer volunteer = (Volunteer)volunteerRepository.findById(volunteerId).orElse(null);
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        model.addAttribute("dogs", dogs);
        model.addAttribute("users", users);
        model.addAttribute("type", loggedInUser.getRole());
        model.addAttribute("volunteer", volunteer);
        return "staff_admin_update_volunteers";

    }

    @PostMapping("/{type}_update_volunteer")
    public String postStaffAdminUpdateVolunteerPage(@RequestParam("volunteerId") UUID volunteerId,@RequestParam("email") String email,
                                                    @RequestParam("job") String job,
                                                    @RequestParam(value = "dogId", required = false) UUID dogId,
                                                    @RequestParam("startTime") String  startTime,
                                                    @RequestParam("stopTime") String  stopTime, Model model, HttpSession session) throws ParseException {
        User user = userRepository.findByEmail(email).orElse(null);
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElse(null);

        if (dogId != null) {
            Dog dog = dogRepository.findById(dogId).orElse(null);
            volunteer.setDog(dog);
        }

        volunteer.setUser(user);
        volunteer.setJob(job);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date startDate = dateFormat.parse(startTime);
        Date stopDate = dateFormat.parse(stopTime);
        volunteer.setStartTime(startDate);
        volunteer.setStopTime(stopDate);

        volunteerRepository.save(volunteer);
        List<Volunteer> filteredVolunteers= volunteerService.getAllVolunteers();
        User loggedInUser =  (User)session.getAttribute("loggedInUser");
        model.addAttribute("type", loggedInUser.getRole());
        model.addAttribute("filteredVolunteers", filteredVolunteers);
        return "staff_admin_manage_volunteers";
    }
    @GetMapping("/{type}_add_volunteer")
    public String getStaffAdminAddVolunteerPage(Model model, HttpSession session){
        List<Dog> dogs = dogService.getAllDogs();
        List<User> users = userService.listAllUsers("User");
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        model.addAttribute("dogs", dogs);
        model.addAttribute("users", users);
        model.addAttribute("type", loggedInUser.getRole());
        return "staff_admin_add_volunteers";

    }
    @PostMapping("/{type}_add_volunteer")
    public String postStaffAdminAddVolunteerPage(@RequestParam("email") String email,
                                                    @RequestParam("job") String job,
                                                    @RequestParam(value = "dogId", required = false) UUID dogId,
                                                    @RequestParam("startTime") String  startTime,
                                                    @RequestParam("stopTime") String  stopTime, Model model, HttpSession session) throws ParseException {
        User user = userRepository.findByEmail(email).orElse(null);
        Dog dog = dogRepository.findById(dogId).orElse(null);
        Volunteer volunteer = new Volunteer();
        volunteer.setDog(dog);
        volunteer.setUser(user);
        volunteer.setJob(job);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date startDate = dateFormat.parse(startTime);
        Date stopDate = dateFormat.parse(stopTime);
        volunteer.setStartTime(startDate);
        volunteer.setStopTime(stopDate);
        volunteerRepository.save(volunteer);
        List<Volunteer> filteredVolunteers= volunteerService.getAllVolunteers();
        User loggedInUser =  (User)session.getAttribute("loggedInUser");
        model.addAttribute("type", loggedInUser.getRole());
        model.addAttribute("filteredVolunteers", filteredVolunteers);
        return "staff_admin_manage_volunteers";
    }

    @PostMapping("/{type}_delete_volunteer")
    public String deleteVolunteer(@RequestParam("volunteerId") UUID volunteerId, Model model, HttpSession session) {
        volunteerService.deleteVolunteer(volunteerId);

        List<Volunteer> filteredVolunteers = volunteerService.getAllVolunteers();
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        model.addAttribute("type", loggedInUser.getRole());
        model.addAttribute("filteredVolunteers", filteredVolunteers);

        return "staff_admin_manage_volunteers";
    }

}

