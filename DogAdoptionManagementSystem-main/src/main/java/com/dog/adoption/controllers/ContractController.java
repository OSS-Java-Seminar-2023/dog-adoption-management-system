package com.dog.adoption.controllers;

import com.dog.adoption.models.Contract;
import com.dog.adoption.models.Dog;
import com.dog.adoption.models.DogVaccine;
import com.dog.adoption.models.User;
import com.dog.adoption.models.WaitingList;
import com.dog.adoption.repository.ContractRepository;
import com.dog.adoption.repository.UserRepository;
import com.dog.adoption.repository.WaitingListRepository;
import com.dog.adoption.services.ContractService;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.UserService;
import com.dog.adoption.services.WaitingListService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ContractController {
    @Autowired
    private final UserRepository userRepository;
    private final WaitingListRepository waitingListRepository;
    private final ContractService contractService;
    private final DogService dogService;
    private final UserService userService;
    private final WaitingListService waitingListService;

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/user_contract")
    public String getUserContractPage(Model model, HttpSession session) {

        Dog dog = (Dog)session.getAttribute("selectedDog");
        User user =(User)session.getAttribute("loggedInUser");
        List<DogVaccine> dogVaccines = (List<DogVaccine>)session.getAttribute("dogVaccines");

        model.addAttribute("dog", dog);
        model.addAttribute("user", user);
        model.addAttribute("dogVaccines", dogVaccines);

        return "user_contract";
    }

    @PostMapping("/submit-user-contract")
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
            Dog savedDog = dogService.updateDog(dog, "Pending");

            // Create and save the contract
            Contract contract = new Contract();
            contract.setUser(savedUser);
            contract.setDog(savedDog);
            contract.setStatus("Submitted");

            Date currentDate = new Date();
            contract.setDateOfContract(currentDate);

            WaitingList waitingList = new WaitingList();
            waitingList.setUser(savedUser);
            waitingList.setDog(savedDog);
            waitingList.setDateOfApplication(currentDate);

            Contract savedContract = contractService.saveContract(contract);
            WaitingList savedWaitingList = waitingListService.saveWaitingList(waitingList);

            // Add the updated user back to the session
            session.setAttribute("loggedInUser", savedUser);

            return "redirect:/user_page";
        }
    }

    @GetMapping("/user_contract_progress_tracking")
    public String getContractProgressPage(Model model, HttpSession session) {

        Dog dog = (Dog)session.getAttribute("selectedDog");
        User user =(User)session.getAttribute("loggedInUser");
        List<Contract> contracts = contractService.getAllContracts(user.getId());

        model.addAttribute("dog", dog);
        model.addAttribute("user", user);
        model.addAttribute("contracts", contracts);

        return "user_contract_progress_tracking";
    }

    @GetMapping("/{type}_manage_contracts")
    public String getStaffAdminManageContractsPage(
            @RequestParam(name = "dogId", required = false) UUID dogId,
            @RequestParam(name = "status", required = false) String status,
            Model model,
            HttpSession session
    ) {
        List<Contract> filteredContracts;
        List<Dog> dogs= dogService.getAllDogs();
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        filteredContracts = contractService.getFilteredContracts(dogId, status
        );

        model.addAttribute("filteredContracts", filteredContracts);
        model.addAttribute("dogs", dogs);
        model.addAttribute("type", loggedInUser.getRole());
        return "staff_admin_manage_contracts";
    }

    @PostMapping("/{type}_update_contract")
    public String updateContractPage(Model model, HttpSession session, @ModelAttribute Contract updatedContract, @RequestParam("contractId") UUID contractId) {
        Contract contract = contractRepository.getById(contractId);
        contract.setStatus(updatedContract.getStatus());

        if (contract.getStatus().equals("Declined")) {
            User contractUser = contract.getUser();
            List<WaitingList> waitingList = waitingListService.getWaitingListByDogId(contract.getDog().getId());
            Iterator<WaitingList> iterator = waitingList.iterator();
            while (iterator.hasNext()) {
                WaitingList waitingEntry = iterator.next();
                if (waitingEntry.getUser().getId().equals(contractUser.getId())) {
                    waitingListRepository.delete(waitingEntry); // Remove the entry from the waiting list and database
                    iterator.remove(); // Remove the entry from the list
                }
            }
        }
        else if (contract.getStatus().equals("Approved")){
            Dog contractDog = contract.getDog();
            contractDog.setAdoptionStatus("Adopted");
            List<WaitingList> waitingList = waitingListService.getWaitingListByDogId(contract.getDog().getId());
            Iterator<WaitingList> iterator = waitingList.iterator();
            while (iterator.hasNext()) {
                WaitingList waitingEntry = iterator.next();
                waitingListRepository.delete(waitingEntry); // Remove the entry from the waiting list and database
                iterator.remove(); // Remove the entry from the list

            }
            List<Contract> contracts = contractRepository.findByDogId(contract.getDog().getId());
            Iterator<Contract> contractIterator = contracts.iterator();
            while (contractIterator.hasNext()){
                Contract contractEntry = contractIterator.next();
                if (contractEntry.getUser().getId() != contract.getUser().getId()){
                    contractEntry.setStatus("Declined");
                    contractRepository.save(contractEntry);
                }
            }
        }
        contractRepository.save(contract);
        User loggedInUser =  (User)session.getAttribute("loggedInUser");
        model.addAttribute("type", loggedInUser.getRole());
        if (loggedInUser.getRole().equals("Staff")){
            return "redirect:/staff_manage_contracts?type=Staff";
        }
        else if (loggedInUser.getRole().equals("Admin")){
            return "redirect:/admin_manage_contracts?type=Admin";
        }
        return "error";
    }

    @GetMapping("/{type}_add_contract")
    public String getStaffAdminAddContract(Model model, HttpSession session){
        List<Dog> dogs = dogService.getAllDogs();
        dogs = dogs.stream()
                .filter(dog -> !"adopted".equalsIgnoreCase(dog.getAdoptionStatus()))
                .collect(Collectors.toList());

        List<User>  users = userService.listAllUsers("User");
        model.addAttribute("users", users);
        model.addAttribute("dogs", dogs);
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        model.addAttribute("type", loggedInUser.getRole());
        return "staff_admin_add_contract";
    }

    @PostMapping("/submit-add-contract")
    public String postStaffAdminAddContract(@RequestParam("userId") UUID userId, @RequestParam("dogId") UUID dogId, @RequestParam("oib") Long oib, Model model, HttpSession session) {
        User loggedInUser = (User)session.getAttribute("loggedInUser");
        User user = userService.getUserById(userId);
        Dog dog = dogService.getDogById(dogId);
        List<Contract> previousContracts = contractService.getContractByUserAndDog(user.getId(), dog.getId());
        model.addAttribute("previousContract", previousContracts);

        if (!previousContracts.isEmpty() || !previousContracts.stream().allMatch(contract -> "Declined".equals(contract.getStatus()))) {
            model.addAttribute("previousContractExists", true);
            if (loggedInUser.getRole().equals("Staff")) {
                return "redirect:/staff_add_contract";
            }
            else if (loggedInUser.getRole().equals("Admin")){
                return "redirect:/admin_add_contract";
            }
            return "error";

        }
        Contract newContract = new Contract();
        newContract.setStatus("Submitted");
        Date currentDate = new Date();
        newContract.setDateOfContract(currentDate);
        newContract.setDog(dog);
        newContract.setUser(user);
        user.setOib(oib);
        userRepository.save(user);
        contractRepository.save(newContract);

        if (loggedInUser.getRole().equals("Staff")){
            return "redirect:/staff_manage_contracts";
        }
        else if (loggedInUser.getRole().equals("Admin")){
            return "redirect:/admin_manage_contracts";
        }
        return "error";
    }

}