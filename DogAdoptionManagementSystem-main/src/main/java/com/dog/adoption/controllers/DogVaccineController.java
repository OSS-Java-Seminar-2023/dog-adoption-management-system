package com.dog.adoption.controllers;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.DogVaccine;
import com.dog.adoption.models.User;
import com.dog.adoption.models.Vaccine;
import com.dog.adoption.repository.DogVaccineRepository;
import com.dog.adoption.repository.VaccineRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.DogVaccineService;
import com.dog.adoption.services.VaccineService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class DogVaccineController {
    private final DogService dogService;
    private final VaccineService vaccineService;
    private final DogVaccineService dogVaccineService;
    private VaccineRepository vaccineRepository;
    private DogVaccineRepository dogVaccineRepository;

    @GetMapping("/update_dog_vaccine" )
    public String getDogVaccineUpdatePage(Model model, HttpSession session, @RequestParam("dog_id") UUID dog_id) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        Dog dog = dogService.getDogById(dog_id);
        List<Vaccine> allVaccines = vaccineService.listAllVaccines();
        List<DogVaccine> dogVaccines = dogVaccineService.getDogVaccinesByDogId(dog_id);

        // Separate the checked and not taken vaccines
        List<Vaccine> checkedVaccines = new ArrayList<>();
        List<Vaccine> notTakenVaccines = new ArrayList<>();

        for (Vaccine vaccine : allVaccines) {
            if (dogVaccines.stream().anyMatch(dv -> dv.getVaccine().equals(vaccine))) {
                checkedVaccines.add(vaccine);
            } else {
                notTakenVaccines.add(vaccine);
            }
        }

        // Combine checked and not taken vaccines, with checked ones first
        List<Vaccine> sortedVaccines = new ArrayList<>();
        sortedVaccines.addAll(checkedVaccines);
        sortedVaccines.addAll(notTakenVaccines);

        model.addAttribute("dog", dog);
        model.addAttribute("type", loggedInUser.getRole());
        model.addAttribute("vaccines", sortedVaccines);
        model.addAttribute("dog_vaccines", dogVaccines);
        session.setAttribute("selectedDog", dog);

        return "staff_admin_update_dog_vaccine";
    }

    @PostMapping("/submit-dogVaccine-update")
    public String submitDogVaccineUpdate(Model model,HttpSession session,
                                         @RequestParam(value = "vaccines", required = false) List<String> selectedVaccines) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        Dog dog = (Dog)session.getAttribute("selectedDog");
        session.removeAttribute("selectedDog");
        dogVaccineService.updateVaccinesList(dog, selectedVaccines);

        if (loggedInUser.getRole().equals("Staff")){
            return "redirect:/staff_manage_dogs";
        }
        else if (loggedInUser.getRole().equals("Admin")){
            return "redirect:/admin_manage_dogs";
        }
        return "error";
    }

}