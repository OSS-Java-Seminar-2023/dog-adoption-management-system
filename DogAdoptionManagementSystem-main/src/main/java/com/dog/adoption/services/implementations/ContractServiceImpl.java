package com.dog.adoption.services.implementations;

import com.dog.adoption.models.Contract;
import com.dog.adoption.models.Dog;
import com.dog.adoption.models.User;
import com.dog.adoption.repository.ContractRepository;
import com.dog.adoption.services.ContractService;
import com.dog.adoption.services.DogService;
import com.dog.adoption.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final UserService userService;
    private final DogService dogService;

    @Override
    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public List<Contract> getAllContracts(UUID userId) {
        User user = userService.getUserById(userId);

        if (user != null) {
            return contractRepository.findByUser(user); // Retrieve contracts for the user
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Contract> getContractByUserAndDog(UUID userId, UUID dogId) {
        User user = userService.getUserById(userId);
        Dog dog = dogService.getDogById(dogId);

        return contractRepository.getContractByUserAndDog(user, dog);
    }

}
