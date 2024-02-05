package com.dog.adoption.controllers;

import com.dog.adoption.data.UserData;
import com.dog.adoption.repository.UserRepository;
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

import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getHomePage() throws Exception {
        mockMvc.perform(get("/home")).andExpect(status().isOk());

    }

    @Test
    void getRegisterPage() throws Exception{
        mockMvc.perform(get("/register")).andExpect(status().isOk()).andExpect(model().attribute("registerRequest", instanceOf(UserData.class)));
    }

    @Test
    void register() throws Exception{
        mockMvc.perform(get("/register")).andExpect(status().isOk()).andExpect(model().attribute("registerRequest", instanceOf(UserData.class)));
    }

    @Test
    void getLoginPage() throws Exception{
        mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(model().attribute("loginRequest", instanceOf(UserData.class)));
    }

    @Test
    void login() throws Exception{
        mockMvc.perform(get("/user_page")).andExpect(status().isOk()).andExpect(model().attribute("loggedInUser", instanceOf(UserData.class)));
    }


    @Test
    void  getUserPage() throws Exception{
        mockMvc.perform(get("/user_page")).andExpect(status().isOk()).andExpect(model().attribute("user", instanceOf(UserData.class)));

    }

    @Test
    void getContactPage() throws Exception {
        mockMvc.perform(get("/contract")).andExpect(status().isOk());
    }

    @Test
    void getMyProfile() throws Exception {
        mockMvc.perform(get("/user_my_profile")).andExpect(status().isOk());
    }

    @Test
    void updateProfile() throws Exception{
        UUID userId = UUID.randomUUID();
        UserData updateUser = new UserData();

        mockMvc.perform(get("/user_my_profile")).andExpect(status().isOk()).andExpect(model().attribute("user", instanceOf(UserData.class)));
    }

    @Test
    void getStaffPage() throws Exception{
        mockMvc.perform(get("/staff_page")).andExpect(status().isOk()).andExpect(model().attribute("type", instanceOf(String.class)));
    }

    @Test
    void  getStaffProfile() throws Exception{
        mockMvc.perform(get("/staff_my_profile")).andExpect(status().isOk()).andExpect(model().attribute("user", instanceOf(UserData.class)));
    }

    @Test
    void postStaffUpdatePage()throws Exception {
        UUID userId = UUID.randomUUID();
        UserData updateUser = new UserData();

        mockMvc.perform(get("/staff_my_profile")).andExpect(status().isOk()).andExpect(model().attribute("user", instanceOf(UserData.class)));
    }

    @Test
    void getAdminPage() throws Exception {
        mockMvc.perform(get("/admin_page")).andExpect(status().isOk());
    }

    @Test
    void getAdminList() throws Exception{

        mockMvc.perform(get("/admin_manage_staff_user_admin")).andExpect(status().isOk()).andExpect(model().attribute("type", instanceOf(String.class))).andExpect(model().attribute("users", instanceOf(List.class)));
    }

    @Test
    void getAdminAddPage() throws Exception {
        UUID userId = UUID.randomUUID();
        UserData updateUser = new UserData();
        mockMvc.perform(get("/admin_add")).andExpect(status().isOk()).andExpect(model().attribute("type", instanceOf(String.class))).andExpect(model().attribute("registerRequest", instanceOf(UserData.class)));

    }

}