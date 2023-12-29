package com.dog.adoption.controllers;

import com.dog.adoption.models.Dog;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.services.DogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class DogController {
    private final DogService dogService;

    @Autowired
    private DogRepository dogRepository;

    @GetMapping("/user_page/user_dogs")
    public String getUserDogsPage(Model model) {
        List<Dog> dogs = dogService.listAllDogs();
        model.addAttribute("dogs", dogs);
        return "user_dogs";
    }


}
