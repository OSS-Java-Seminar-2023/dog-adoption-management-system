package com.dog.adoption.services;
import com.dog.adoption.models.Volunteer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

public interface VolunteerService {
    void saveVolunteer(Volunteer volunteer);
    List<Volunteer> getVolunteersFromStartTime(Date start);
    List<Volunteer> getVolunteersByTimePeriodOrBetweenStartStopTime(Date startStartTime, Date endStartTime, Date startStopTime, Date endStopTime);
    List<Volunteer> getVolunteersByJob(String job);
    List<Volunteer> getAllVolunteers();
    List<Volunteer> getVolunteersByDogId(UUID dogId);
    List<Volunteer> getExecutedVolunteers();
    List<Volunteer> getPendingVolunteers();
    void deleteVolunteer(UUID volunteerId);

}