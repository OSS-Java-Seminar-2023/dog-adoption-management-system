package com.dog.adoption.services;

import com.dog.adoption.models.Contract;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public interface ContractService {
    Contract saveContract(Contract contract);

    List<Contract> getAllContracts(UUID userId);

    List<Contract> getContractByUserAndDog(UUID userId, UUID dogId);

    List<Contract> getAllContracts();


}