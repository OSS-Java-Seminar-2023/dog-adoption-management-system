package com.dog.adoption.repository;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Contract;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {
    List<Contract> findByUser(User user);
    List<Contract> getContractByUserAndDog(User user, Dog dog);
    List<Contract> findByDogIdAndDateOfContractAndStatus(UUID dogId, Date dateOfContract, String status);
    List<Contract> findByDogIdAndDateOfContract(UUID dogId, Date dateOfContract);
    List<Contract> findByDogIdAndStatus(UUID dogId, String status);
    List<Contract> findByDateOfContractAndStatus(Date dateOfContract, String status);
    List<Contract> findByDogId(UUID dogId);
    List<Contract> findByDateOfContract(Date dateOfContract);
    List<Contract> findByStatus(String status);
}
