package com.dog.adoption.services.implementations;

import com.dog.adoption.models.Dog;
import com.dog.adoption.models.DogVaccine;
import com.dog.adoption.repository.DogVaccineRepository;
import com.dog.adoption.repository.VaccineRepository;
import com.dog.adoption.services.DogVaccineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DogVaccineServiceImpl implements DogVaccineService {
    private final DogVaccineRepository dogVaccineRepository;
    private final VaccineRepository vaccineRepository;

    @Override
    public List<DogVaccine> getDogVaccinesByDogId(UUID dogId) {
        return dogVaccineRepository.findByDogId(dogId);
    }

    @Override
    public void updateVaccinesList(Dog dog, List<String> selectedVaccines) {
        List<DogVaccine> dogVaccines = getDogVaccinesByDogId(dog.getId());
        if(selectedVaccines != null) {
            for (String vaccine : selectedVaccines) {
                boolean containsVaccine = dogVaccines.stream()
                        .anyMatch(dogVaccine -> dogVaccine.getVaccine().getVaccineName().equals(vaccine));

                if (!containsVaccine) {
                    DogVaccine newDogVaccine = new DogVaccine();
                    newDogVaccine.setDog(dog);
                    newDogVaccine.setVaccine(vaccineRepository.findByVaccineName(vaccine));
                    dogVaccineRepository.save(newDogVaccine);

                }

            }
        }
    }


}
