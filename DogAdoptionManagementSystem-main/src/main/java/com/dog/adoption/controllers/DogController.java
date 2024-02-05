package com.dog.adoption.controllers;

import com.dog.adoption.data.DogData;
import com.dog.adoption.models.Dog;
import com.dog.adoption.models.DogVaccine;
import com.dog.adoption.models.User;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.DogVaccineService;
import com.dog.adoption.utils.ValidationUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class DogController {
    private final DogService dogService;
    private DogVaccineService dogVaccineService;

    @Autowired
    private DogRepository dogRepository;


    @GetMapping("/user_dogs")
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

    @GetMapping("/user_dog_info")
    public String getDogInfo(@RequestParam("dog_id") UUID dogId, Model model, HttpSession session) {
        Dog selectedDog = dogService.getDogById(dogId);
        List<DogVaccine> dogVaccines = dogVaccineService.getDogVaccinesByDogId(dogId);

        session.setAttribute("selectedDog", selectedDog);
        session.setAttribute("dogVaccines", dogVaccines);
        model.addAttribute("selectedDog", selectedDog);
        model.addAttribute("dogVaccines", dogVaccines);
        return "user_dog_info";
    }

    @GetMapping("/{type}_manage_dogs")
    public String getStaffManageDogsPage(
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "breed", required = false) String breed,
            @RequestParam(name = "ageCategory", required = false) String ageCategory,
            @RequestParam(name = "castrated", required = false, defaultValue = "false") boolean castrated,
            @RequestParam(name = "sterilised", required = false, defaultValue = "false") boolean sterilised,
            @RequestParam(name = "gender", required = false) String gender,
            Model model, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        List<Dog> dogs = dogService.listAllDogs(size, breed, ageCategory, castrated, sterilised, gender);
        model.addAttribute("dogs", dogs);
        model.addAttribute("type", loggedInUser.getRole());
        return "staff_admin_manage_dogs";
    }

    @GetMapping("/add_dog" )
    public String getAddDogPage(Model model, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        model.addAttribute("newDog", new Dog());
        model.addAttribute("type",  loggedInUser.getRole());
        return "staff_admin_add_dog";
    }

    @PostMapping("/submit-add-dog")
    public String postAddDogPage(@ModelAttribute DogData request,
                              @RequestParam("dogImage") MultipartFile dogImage,
                              Model model, HttpSession session) {
        try {
            User loggedInUser = (User)session.getAttribute("loggedInUser");
            model.addAttribute("type",  loggedInUser.getRole());
            Dog registeredDog = dogService.addDog(request, dogImage);
            if (registeredDog == null) {
                return "error";
            }
            List<Dog> dogs = dogRepository.findAll();
            model.addAttribute("dogs", dogs);
            return "staff_admin_manage_dogs";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("newDog", new Dog());
            return "staff_admin_add_dog";
        }
    }

    @GetMapping("/update_dog")
    public String getDogUpdatePage(Model model, HttpSession session, @RequestParam("dog_id") UUID dog_id) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        Dog dog = dogService.getDogById(dog_id);
        model.addAttribute("dog",dog);
        model.addAttribute("type",  loggedInUser.getRole());
        session.setAttribute("selectedDog", dog);
        return "staff_admin_update_dog";
    }

    @PostMapping("/submit-dog-update")
    public String postDogUpdatePage(@ModelAttribute Dog updatedDog, @RequestParam("dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
                                    HttpSession session) {
        Dog dog = (Dog)session.getAttribute("selectedDog");
        User user = (User)session.getAttribute("loggedInUser");

        if (dog != null) {
            if (!ValidationUtils.isStringNullOrEmpty(dog.getName())) {
                dog.setName(updatedDog.getName());
            }
            if (!ValidationUtils.isLongNullOrEmpty(dog.getMicrochip())) {
                dog.setMicrochip(updatedDog.getMicrochip());
            }
            if (!ValidationUtils.isStringNullOrEmpty(dog.getGender())) {
                dog.setGender(updatedDog.getGender());
            }
            if (!ValidationUtils.isStringNullOrEmpty(dog.getBreed())) {
                dog.setBreed(updatedDog.getBreed());
            }
            if (!ValidationUtils.isStringNullOrEmpty(dog.getSize())) {
                dog.setSize(updatedDog.getSize());
            }
            if (!ValidationUtils.isStringNullOrEmpty(dog.getSterilised())) {
                dog.setSterilised(updatedDog.getSterilised());
            }
            if (!ValidationUtils.isStringNullOrEmpty(dog.getCastrated())) {
                dog.setCastrated(updatedDog.getCastrated());
            }
            dog.setNote(updatedDog.getNote());

            if (!ValidationUtils.isStringNullOrEmpty(updatedDog.getImage())) {
                dog.setImage(updatedDog.getImage());
            }

            dog.setDateOfBirth(updatedDog.getDateOfBirth());
            dogRepository.save(dog);
        }
        session.removeAttribute("selectedDog");

        if (user.getRole().equals("Staff")){
            return "redirect:/staff_manage_dogs";
        }
        else if (user.getRole().equals("Admin")){
            return "redirect:/admin_manage_dogs";
        }
        return "error";
    }

}