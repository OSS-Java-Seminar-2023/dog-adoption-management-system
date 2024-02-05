package com.dog.adoption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Dog;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DogRepository extends JpaRepository<Dog, UUID> {
    Optional<Dog> findByBreed(String breed);
    Optional<Dog> findBySize(String size);
    Optional<Dog> findBySterilised(String sterilised);
    Optional<Dog> findByCastrated(String castrated);

}
