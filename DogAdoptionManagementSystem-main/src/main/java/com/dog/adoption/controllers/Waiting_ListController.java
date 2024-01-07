package com.dog.adoption.controllers;
import com.dog.adoption.models.Dog;
import com.dog.adoption.models.Waiting_List;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.Waiting_ListService;
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
public class Waiting_ListController {
    @Autowired
    private Waiting_ListService waitingListService;
    @Autowired
    private DogService dogService;

    @GetMapping("/user_page/user_dogs/user_dog_info/user_waiting_list")
    public String getUserWaitingList(@RequestParam("dog_id") UUID dogId, Model model, HttpSession session) {
        Dog selectedDog = (Dog)session.getAttribute("selectedDog");

        if (selectedDog != null) {
            List<Waiting_List> waitingList = waitingListService.getWaitingListByDogId(dogId);
            int numUsersApplied = waitingList.size();

            model.addAttribute("selectedDog", selectedDog);
            model.addAttribute("numUsersApplied", numUsersApplied);

            return "user_waiting_list";
        } else {
            return "error";
        }
    }
}
