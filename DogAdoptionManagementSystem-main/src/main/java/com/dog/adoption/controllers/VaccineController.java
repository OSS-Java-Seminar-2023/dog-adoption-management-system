package com.dog.adoption.controllers;

import com.dog.adoption.models.User;
import com.dog.adoption.models.Vaccine;
import com.dog.adoption.repository.VaccineRepository;
import com.dog.adoption.services.VaccineService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class VaccineController {
    private final VaccineService vaccineService;

    @Autowired
    private VaccineRepository vaccineRepository;
    @GetMapping("{type}_manage_vaccines")
    public String getStaffAdminVaccineList(Model model, HttpSession session){
        List<Vaccine> vaccines = vaccineService.listAllVaccines();
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        model.addAttribute("type", loggedInUser.getRole());
        model.addAttribute("vaccines",  vaccines);
        return "staff_admin_manage_vaccines";
    }
    @PostMapping("/add_vaccine")
    public String postStaffAdminVaccineAdd(Model model, HttpSession session, @RequestParam("newVaccineName") String newVaccineName){
        Vaccine newVaccine = new Vaccine();
        newVaccine.setVaccineName(newVaccineName);
        List<Vaccine> vaccines = vaccineService.listAllVaccines();
        for(Vaccine vaccine : vaccines){
            if(vaccine.getVaccineName().equals(newVaccineName)){
                return "error";
            }
        }
        vaccineRepository.save(newVaccine);
        vaccines.add(newVaccine);
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        model.addAttribute("type", loggedInUser.getRole());
        model.addAttribute("vaccines",  vaccines);
        return "staff_admin_manage_vaccines";
    }
}
