package main.repositories;
import main.entities.Waiting_List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface Waiting_ListRepository extends JpaRepository<Waiting_List, UUID> {
}