package com.dog.adoption.controllers;
import com.dog.adoption.data.DogData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class WaitingListControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void getUserWaitingList()  throws Exception {

        mockMvc.perform(get("/user_waiting_list")).andExpect(status().isOk()).andExpect(model().attribute("selectedDog", instanceOf(DogData.class)));

    }

}