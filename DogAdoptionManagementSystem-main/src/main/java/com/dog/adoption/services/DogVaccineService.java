package com.dog.adoption.services;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.DogVaccine;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface DogVaccineService {
    List<DogVaccine> getDogVaccinesByDogId(UUID dogId);
    void updateVaccinesList(Dog dog, List<String> selectedVaccines);

}