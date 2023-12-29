package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Volunteer;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, UUID>{
}
