package com.dog.adoption.controllers;
import com.dog.adoption.repository.ContractRepository;
import com.dog.adoption.services.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;

    @GetMapping("/user_page/user_adoption_progress_tracking")
    public String getAdoptionProgressPage() {
        return "user_adoption_progress_tracking";
    }
}
