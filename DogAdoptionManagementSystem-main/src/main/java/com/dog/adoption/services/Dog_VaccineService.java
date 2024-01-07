package com.dog.adoption.services;

import com.dog.adoption.models.Dog_Vaccine;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface Dog_VaccineService {
    List<Dog_Vaccine> getDogVaccinesByDogId(UUID dogId);

}