package main.repositories;
import main.entities.Dog_Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface Dog_VaccineRepository extends JpaRepository<Dog_Vaccine, UUID> {
}