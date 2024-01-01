package com.dog.adoption.services.implementations;

import com.dog.adoption.models.Dog_Vaccine;
import com.dog.adoption.repository.DogVaccineRepository;
import com.dog.adoption.services.Dog_VaccineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class Dog_VaccineServiceImpl implements Dog_VaccineService {
    private final DogVaccineRepository dog_VaccineRepository;

    @Override
    public List<Dog_Vaccine> getDogVaccinesByDogId(UUID dogId) {
        return dog_VaccineRepository.findByDogId(dogId);
    }
}
