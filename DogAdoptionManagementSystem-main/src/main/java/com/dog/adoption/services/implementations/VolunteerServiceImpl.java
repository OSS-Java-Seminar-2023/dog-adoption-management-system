package com.dog.adoption.services.implementations;

import com.dog.adoption.models.Volunteer;
import com.dog.adoption.repository.VolunteerRepository;
import com.dog.adoption.services.VolunteerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;

    @Override
    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public List<Volunteer> getVolunteersFromStartTime(Date start) {
        return volunteerRepository.findByStartTimeGreaterThanEqual(start);
    }

    @Override
    public List<Volunteer> getVolunteersByTimePeriodOrBetweenStartStopTime(Date startStartTime, Date endStartTime, Date startStopTime, Date endStopTime) {
        return volunteerRepository.findByStartTimeBetweenOrStopTimeBetween(
                startStartTime, endStartTime, startStopTime, endStopTime
        );
    }

    @Override
    public List<Volunteer> getVolunteersByJob(String job) {
        return volunteerRepository.findByJob(job);
    }

    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @Override
    public List<Volunteer> getVolunteersByDogId(UUID dogId) {
        return volunteerRepository.findByDogId(dogId);
    }

}
