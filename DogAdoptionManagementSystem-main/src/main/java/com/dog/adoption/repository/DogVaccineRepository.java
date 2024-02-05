package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.DogVaccine;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DogVaccineRepository extends JpaRepository<DogVaccine, UUID>{
    List<DogVaccine> findByDogId(UUID dogId);

}
