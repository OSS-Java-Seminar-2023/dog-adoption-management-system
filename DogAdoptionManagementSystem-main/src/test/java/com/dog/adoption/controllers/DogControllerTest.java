package com.dog.adoption.controllers;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserDogsPage() throws Exception {
        mockMvc.perform(get("/user_dogs"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("dogs", instanceOf(List.class)));
    }

    @Test
    void getDogInfo() throws Exception {
        mockMvc.perform(get("/user_dog_info").param("dog_id", "some-dog-id"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("selectedDog", instanceOf(Dog.class)))
                .andExpect(model().attribute("dogVaccines", instanceOf(List.class)));
    }

    @Test
    void getStaffManageDogsPage() throws Exception {
        mockMvc.perform(get("/staff_manage_dogs"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("dogs", instanceOf(List.class)))
                .andExpect(model().attribute("type", instanceOf(String.class)));
    }

    @Test
    void getAddDogPage() throws Exception {
        mockMvc.perform(get("/add_dog"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("newDog", instanceOf(Dog.class)))
                .andExpect(model().attribute("type", instanceOf(String.class)));
    }

    @Test
    void getDogUpdatePage() throws Exception {
        mockMvc.perform(get("/update_dog").param("dog_id", "some-dog-id"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("dog", instanceOf(Dog.class)))
                .andExpect(model().attribute("type", instanceOf(String.class)))
                .andExpect(request().sessionAttribute("selectedDog", instanceOf(Dog.class)));
    }

    @Test
    void postAddDogPage() throws Exception {
        mockMvc.perform(post("/submit-add-dog")
                        .param("userId", "some-user-id")
                        .param("dogId", "some-dog-id")
                        .param("oib", "123456789")
                        .param("type", "Staff")
                        .sessionAttr("loggedInUser", new User())
                ).andExpect(status().is3xxRedirection()) // Assuming a successful redirection
                .andExpect(redirectedUrl("/staff_manage_dogs"));
    }

    @Test
    void postDogUpdatePage() throws Exception {
        mockMvc.perform(post("/submit-dog-update")
                        .param("dogId", "some-dog-id")
                        .param("updatedDogName", "New Dog Name")
                        .param("dateOfBirth", "2022-02-14")
                        .param("type", "Staff")
                        .sessionAttr("loggedInUser", new User())
                        .sessionAttr("selectedDog", new Dog())
                ).andExpect(status().is3xxRedirection()) // Assuming a successful redirection
                .andExpect(redirectedUrl("/staff_manage_dogs"));
    }

}
