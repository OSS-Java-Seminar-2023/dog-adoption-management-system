package com.dog.adoption.controllers;
import com.dog.adoption.repository.VaccineRepository;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class VaccineControllerTest {

    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void getStaffAdminVaccineList() throws Exception{

        mockMvc.perform(get("/staff_admin_manage_vaccines")).andExpect(status().isOk()).andExpect(model().attribute("type", instanceOf(String.class))).andExpect(model().attribute("Vaccines", instanceOf(List.class)));

    }
    @Test
    void postStaffAdminVaccineAdd() throws Exception{
        mockMvc.perform(get("/staff_admin_manage_vaccines")).andExpect(status().isOk()).andExpect(model().attribute("type", instanceOf(String.class))).andExpect(model().attribute("Vaccines", instanceOf(List.class)));

    }

}