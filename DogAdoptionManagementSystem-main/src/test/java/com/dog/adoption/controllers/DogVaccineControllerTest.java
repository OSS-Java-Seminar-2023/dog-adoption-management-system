package com.dog.adoption.controllers;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.DogVaccine;
import com.dog.adoption.models.User;
import com.dog.adoption.models.Vaccine;
import com.dog.adoption.repository.DogVaccineRepository;
import com.dog.adoption.repository.VaccineRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.DogVaccineService;
import com.dog.adoption.services.VaccineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DogVaccineControllerTest {

    @Mock
    private DogService dogService;
    @Mock
    private VaccineService vaccineService;
    @Mock
    private DogVaccineService dogVaccineService;
    @Mock
    private VaccineRepository vaccineRepository;
    @Mock
    private DogVaccineRepository dogVaccineRepository;

    @InjectMocks
    private DogVaccineController dogVaccineController;

    private MockMvc mockMvc;

    @Test
    void getDogVaccineUpdatePage() throws Exception {
        UUID dogId = UUID.randomUUID();
        Dog dog = new Dog();
        dog.setId(dogId);
        List<Vaccine> allVaccines = new ArrayList<>();
        List<Vaccine> checkedVaccines = new ArrayList<>();
        List<Vaccine> notTakenVaccines = new ArrayList<>();
        List<DogVaccine> dogVaccines = new ArrayList<>();

        when(dogService.getDogById(dogId)).thenReturn(dog);
        when(vaccineService.listAllVaccines()).thenReturn(allVaccines);
        when(dogVaccineService.getDogVaccinesByDogId(dogId)).thenReturn(dogVaccines);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", new User());

        mockMvc.perform(get("/update_dog_vaccine").param("dog_id", dogId.toString())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("dog", dog))
                .andExpect(model().attribute("type", "Staff"))
                .andExpect(model().attribute("vaccines", allVaccines))
                .andExpect(model().attribute("dog_vaccines", dogVaccines))
                .andExpect(request().sessionAttribute("selectedDog", dog));
    }

    @Test
    void submitDogVaccineUpdate() throws Exception {
        UUID dogId = UUID.randomUUID();
        Dog dog = new Dog();
        dog.setId(dogId);
        List<String> selectedVaccines = new ArrayList<>();

        doNothing().when(dogVaccineService).updateVaccinesList(dog, selectedVaccines);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", new User());
        session.setAttribute("selectedDog", dog);

        mockMvc.perform(post("/submit-dogVaccine-update").param("vaccines", "vaccine1", "vaccine2")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/staff_manage_dogs"));
    }

}