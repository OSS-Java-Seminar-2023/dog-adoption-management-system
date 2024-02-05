package com.dog.adoption.controllers;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.User;
import com.dog.adoption.models.Volunteer;
import com.dog.adoption.repository.VolunteerRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.UserService;
import com.dog.adoption.services.VolunteerService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VolunteerControllerTest {

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private DogService dogService;

    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    void getUserVolunteerPage() {
        Model model = mock(Model.class);

        List<Dog> dogs = new ArrayList<>();
        when(dogService.listAllDogs(any(), any(), any(), anyBoolean(), anyBoolean(), any())).thenReturn(dogs);

        String result = volunteerController.getUserVolunteerPage("size", "breed", "age", true, false, "gender", model);

        assertEquals("user_volunteer", result);
        verify(model).addAttribute("dogs", dogs);
        verify(model).addAttribute("volunteer", new Volunteer());
    }

    @Test
    void getVolunteerOptions() {
        HttpSession session = mock(HttpSession.class);
        User loggedInUser = new User();
        loggedInUser.setId(UUID.randomUUID());
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        Volunteer volunteer = new Volunteer();
        volunteer.setDog(new Dog());
        doNothing().when(volunteerService).saveVolunteer(any());

        String result = volunteerController.getVolunteerOptions(volunteer, session);

        assertEquals("redirect:/user_page", result);
        verify(volunteerService).saveVolunteer(volunteer);
    }

    @Test
    void getStaffAdminManageVolunteersPage() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        User loggedInUser = new User();
        loggedInUser.setId(UUID.randomUUID());
        when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        List<Volunteer> filteredVolunteers = new ArrayList<>();
        List<Dog> dogs = new ArrayList<>();
        when(volunteerService.getAllVolunteers()).thenReturn(filteredVolunteers);
        when(dogService.listAllDogs(any(), any(), any(), anyBoolean(), anyBoolean(), any())).thenReturn(dogs);

        String result = volunteerController.getStaffAdminManageVolunteersPage(
                "size", "breed", "age", true, false, "gender",
                false, false, null, null, null, null, model, session
        );

        assertEquals("staff_admin_manage_volunteers", result);
        verify(model).addAttribute("filteredVolunteers", filteredVolunteers);
        verify(model).addAttribute("dogs", dogs);
        verify(model).addAttribute("type", loggedInUser.getRole());
    }

}
