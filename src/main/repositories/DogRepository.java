package main.repositories;
import main.entities.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DogRepository extends JpaRepository<Dog, UUID> {
}