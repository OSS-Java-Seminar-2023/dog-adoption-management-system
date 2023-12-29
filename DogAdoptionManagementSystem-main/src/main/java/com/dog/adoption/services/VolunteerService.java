package com.dog.adoption.services;
import com.dog.adoption.models.Volunteer;
import com.dog.adoption.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

}