package com.dog.adoption.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dog.adoption.models.Volunteer;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, UUID>{
    List<Volunteer> findByStartTimeGreaterThanEqual(Date start);
    List<Volunteer> findByStartTimeBetweenOrStopTimeBetween(Date startStartTime, Date endStartTime, Date startStopTime, Date endStopTime);
    List<Volunteer> findByJob(String job);
    List<Volunteer> findByDogId(UUID dogId);

}
