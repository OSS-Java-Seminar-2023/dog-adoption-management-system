package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Contract;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {
}
