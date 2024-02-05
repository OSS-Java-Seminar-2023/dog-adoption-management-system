package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Vaccine;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String> {
    Vaccine findByVaccineName(String vaccine);

}
