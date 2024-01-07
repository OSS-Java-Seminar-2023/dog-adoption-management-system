package com.dog.adoption.controllers;
import com.dog.adoption.data.DogData;
import com.dog.adoption.models.Dog;
import com.dog.adoption.models.Vaccine;
import com.dog.adoption.models.Dog_Vaccine;
import com.dog.adoption.models.User;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.repository.DogVaccineRepository;
import com.dog.adoption.repository.VaccineRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.VaccineService;
import com.dog.adoption.services.Dog_VaccineService;
import com.dog.adoption.utils.ValidationUtils;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
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
public class Dog_VaccineController {
    private final DogService dogService;
    private final VaccineService vaccineService;
    private final Dog_VaccineService dogVaccineService;
    private VaccineRepository vaccineRepository;
    private DogVaccineRepository dogVaccineRepository;
    @GetMapping({"/staff_page/staff_manage_dogs/update_dog_vaccine", "/admin_page/admin_manage_dogs/update_dog_vaccine"} )
    public String getDogVaccineUpdatePage(Model model, HttpSession session, @RequestParam("dog_id") UUID dog_id) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        Dog dog = dogService.getDogById(dog_id);
        List<Vaccine> vaccines =  vaccineService.listAllVaccines();
        List<Dog_Vaccine> dogVaccines = dogVaccineService.getDogVaccinesByDogId(dog_id);
        model.addAttribute("dog",dog);
        model.addAttribute("type",  loggedInUser.getRole());
        model.addAttribute("vaccines",vaccines);
        model.addAttribute("dog_vaccines", dogVaccines);
        session.setAttribute("selectedDog", dog);
        return "staff_admin_update_dog_vaccine";
    }

    @PostMapping({"/staff_page/staff_manage_dogs/update_dog_vaccine/submit-update", "/admin_page/admin_manage_dogs/submit-update"})
    public String submitDogVaccineUpdate(Model model,HttpSession session,
                                         @RequestParam(value = "vaccines", required = false) List<String> selectedVaccines) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        Dog dog = (Dog)session.getAttribute("selectedDog");
        session.removeAttribute("selectedDog");
        List<Dog_Vaccine> dogVaccines = dogVaccineService.getDogVaccinesByDogId(dog.getId());
        if(selectedVaccines != null) {
            for (String vaccine : selectedVaccines) {
                boolean containsVaccine = dogVaccines.stream()
                        .anyMatch(dogVaccine -> dogVaccine.getVaccine().getVaccineName().equals(vaccine));

                if (!containsVaccine) {
                    Dog_Vaccine newDogVaccine = new Dog_Vaccine();
                    newDogVaccine.setDog(dog);
                    newDogVaccine.setVaccine(vaccineRepository.findByVaccineName(vaccine));
                    dogVaccineRepository.save(newDogVaccine);

                }

            }
        }
        if(loggedInUser.getRole().equals("Staff")){
            return "redirect:/staff_page/staff_manage_dogs";
        }
        else if(loggedInUser.getRole().equals("Admin")){
            return "redirect:/admin_page/admin_manage_dogs";
        }
        return "error";
    }
}
