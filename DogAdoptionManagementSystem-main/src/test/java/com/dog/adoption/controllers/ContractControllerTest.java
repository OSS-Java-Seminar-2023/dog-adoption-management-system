package com.dog.adoption.controllers;
import com.dog.adoption.data.DogData;
import com.dog.adoption.data.UserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.dog.adoption.models.*;
import com.dog.adoption.repository.ContractRepository;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ContractRepository contractRepository;

    @Test
    void getUserContractPage() throws Exception{

        mockMvc.perform(get("/user_contract")).andExpect(status().isOk()).andExpect(model().attribute("user", instanceOf(UserData.class))).andExpect(model().attribute("dog", instanceOf(DogData.class))).andExpect(model().attribute("dogVaccines", instanceOf(List.class)));

    }

    @Test
    void submitAdoptionContract() throws Exception{

        mockMvc.perform(get("/user_page")).andExpect(status().isOk()).andExpect(model().attribute("user", instanceOf(UserData.class))).andExpect(model().attribute("dog", instanceOf(DogData.class)));

    }

    @Test
    void getContractProgressPage() throws Exception {


        mockMvc.perform(get("/user_contract_progress_tracking")).andExpect(status().isOk()).andExpect(model().attribute("user", instanceOf(UserData.class))).andExpect(model().attribute("dog", instanceOf(DogData.class))).andExpect(model().attribute("contracts", instanceOf(List.class)));

    }

    @Test
    void getStaffAdminManageContractsPage() throws Exception {

        mockMvc.perform(get("/staff_admin_manage_contracts")).andExpect(status().isOk()).andExpect(model().attribute("contracts", instanceOf(List.class))).andExpect(model().attribute("dogs", instanceOf(List.class)));


    }
    @Test
    void updateContractPage() throws Exception{
        UUID contractId = UUID.randomUUID();
        Contract contract = new Contract();
        mockMvc.perform(get("/staff_admin_manage_contracts")).andExpect(status().isOk());

    }

}