package com.dog.adoption.controllers;
import com.dog.adoption.models.Dog;
import com.dog.adoption.models.WaitingList;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.WaitingListService;
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
public class WaitingListController {
    @Autowired
    private WaitingListService waitingListService;
    @Autowired
    private DogService dogService;

    @GetMapping("/user_waiting_list")
    public String getUserWaitingList(@RequestParam("dog_id") UUID dogId, Model model, HttpSession session) {
        Dog selectedDog = (Dog)session.getAttribute("selectedDog");

        if (selectedDog != null) {
            List<WaitingList> waitingList = waitingListService.getWaitingListByDogId(dogId);
            int numUsersApplied = waitingList.size();

            model.addAttribute("selectedDog", selectedDog);
            model.addAttribute("numUsersApplied", numUsersApplied);

            return "user_waiting_list";
        } else {
            return "error";
        }
    }
}
