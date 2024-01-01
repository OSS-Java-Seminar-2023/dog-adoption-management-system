package com.dog.adoption.controllers;

import com.dog.adoption.models.*;
import com.dog.adoption.repository.ContractRepository;
import com.dog.adoption.services.ContractService;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.UserService;
import com.dog.adoption.services.Waiting_ListService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final DogService dogService;
    private final UserService userService;
    private final Waiting_ListService waitingListService;

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/user_page/user_dogs/user_dog_info/user_contract")
    public String getUserContractPage(Model model, HttpSession session) {

        Dog dog = (Dog)session.getAttribute("selectedDog");
        User user =(User)session.getAttribute("loggedInUser");
        List<Dog_Vaccine> dogVaccines = (List<Dog_Vaccine>)session.getAttribute("dogVaccines");

        model.addAttribute("dog", dog);
        model.addAttribute("user", user);
        model.addAttribute("dogVaccines", dogVaccines);

        return "user_contract";
    }

    @PostMapping("/user_page/user_dogs/user_dog_info/user_contract/submit-user-contract")
    public String submitAdoptionContract(Model model, @RequestParam("oib") Long oib, HttpSession session) {

        Dog dog = (Dog)session.getAttribute("selectedDog");
        User user =(User)session.getAttribute("loggedInUser");
        user.setOib(oib);

        model.addAttribute("user", user);
        model.addAttribute("dog", dog);

        // Check if the user has previously submitted a contract for this dog
        List<Contract> previousContracts = contractService.getContractByUserAndDog(user.getId(), dog.getId());
        model.addAttribute("previousContract", previousContracts);

        // If a previous contract exists and its status is not 'Declined', prevent submission
        if (!previousContracts.isEmpty() || !previousContracts.stream().allMatch(contract -> "Declined".equals(contract.getStatus()))) {
            model.addAttribute("previousContractExists", true);
            return "user_contract";
        }
        else {
            User savedUser = userService.updateUser(user);
            Dog savedDog = dogService.updateDog(dog);

            // Create and save the contract
            Contract contract = new Contract();
            contract.setUser(savedUser);
            contract.setDog(savedDog);
            contract.setStatus("Submitted");

            Date currentDate = new Date();
            contract.setDateOfContract(currentDate);

            Waiting_List waitingList = new Waiting_List();
            waitingList.setUser(savedUser);
            waitingList.setDog(savedDog);
            waitingList.setDateOfApplication(currentDate);

            Contract savedContract = contractService.saveContract(contract);
            Waiting_List savedWaitingList = waitingListService.saveWaitingList(waitingList);

            // Add the updated user back to the session
            session.setAttribute("loggedInUser", savedUser);

            return "redirect:/user_page";
        }
    }

    @GetMapping("/user_page/user_contract_progress_tracking")
    public String getContractProgressPage(Model model, HttpSession session) {

        Dog dog = (Dog)session.getAttribute("selectedDog");
        User user =(User)session.getAttribute("loggedInUser");
        List<Contract> contracts = contractService.getAllContracts(user.getId());

        model.addAttribute("dog", dog);
        model.addAttribute("user", user);
        model.addAttribute("contracts", contracts);

        return "user_contract_progress_tracking";
    }
}
