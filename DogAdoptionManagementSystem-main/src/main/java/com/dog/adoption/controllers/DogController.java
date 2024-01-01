package com.dog.adoption.controllers;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.Dog_Vaccine;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.Dog_VaccineService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class DogController {
    private final DogService dogService;
    private final Dog_VaccineService dogVaccineService;

    @Autowired
    private DogRepository dogRepository;

//    @GetMapping("/user_page/user_dogs")
//    public String getUserDogsPage(Model model) {
//        List<Dog> dogs = dogService.listAllDogs();
//        model.addAttribute("dogs", dogs);
//        return "user_dogs";
//    }

    @GetMapping("/user_page/user_dogs")
    public String getUserDogsPage(
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "breed", required = false) String breed,
            @RequestParam(name = "ageCategory", required = false) String ageCategory,
            @RequestParam(name = "castrated", required = false, defaultValue = "false") boolean castrated,
            @RequestParam(name = "sterilised", required = false, defaultValue = "false") boolean sterilised,
            @RequestParam(name = "gender", required = false) String gender,
            Model model) {

        List<Dog> dogs = dogService.listAllDogs(size, breed, ageCategory, castrated, sterilised, gender);

        model.addAttribute("dogs", dogs);
        return "user_dogs";
    }

    @GetMapping("/user_page/user_dogs/user_dog_info")
    public String getDogInfo(@RequestParam("dog_id") UUID dogId, Model model, HttpSession session) {
        Dog selectedDog = dogService.getDogById(dogId);
        List<Dog_Vaccine> dogVaccines = dogVaccineService.getDogVaccinesByDogId(dogId);

        session.setAttribute("selectedDog", selectedDog);
        session.setAttribute("dogVaccines", dogVaccines);
        model.addAttribute("selectedDog", selectedDog);
        model.addAttribute("dogVaccines", dogVaccines);
        return "user_dog_info";
    }
}
