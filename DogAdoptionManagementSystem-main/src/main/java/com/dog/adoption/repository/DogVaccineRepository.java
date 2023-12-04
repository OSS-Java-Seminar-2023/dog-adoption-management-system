package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Dog_Vaccine;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public interface DogVaccineRepository extends JpaRepository<Dog_Vaccine, UUID>{
}
