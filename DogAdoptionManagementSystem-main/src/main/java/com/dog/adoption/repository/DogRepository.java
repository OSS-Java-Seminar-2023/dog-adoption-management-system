package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Dog;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public interface DogRepository extends JpaRepository<Dog, UUID>{
}
