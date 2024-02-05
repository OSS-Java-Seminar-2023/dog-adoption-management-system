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
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


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
            return contractRepository.findByUser(user);
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

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
    @Override
    public List<Contract> getFilteredContracts(UUID dogId, String status) {
        List<Contract> allContracts = getAllContracts();

        return allContracts.stream()
                .filter(contract -> StringUtils.isEmpty(status) || status.equalsIgnoreCase(contract.getStatus()))
                .filter(contract -> (dogId == null || dogId.equals(contract.getDog().getId())))
                .collect(Collectors.toList());

    }
}
