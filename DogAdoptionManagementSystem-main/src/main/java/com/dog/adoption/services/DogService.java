package com.dog.adoption.services;

import com.dog.adoption.data.DogData;
import com.dog.adoption.models.Dog;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface DogService {
    boolean inputValidation(DogData request);
    String saveDogImage(MultipartFile dogImage) throws IOException;
    Dog addDog(DogData request, MultipartFile dogImage);
    List<Dog> listAllDogs(String size, String breed, String ageCategory, boolean castrated, boolean sterilised, String gender);
    Optional<Dog> findDogBySterilised(String sterilised);
    Optional<Dog> findDogBySize(String size);
    Optional<Dog> findDogByBreed(String breed);
    Optional<Dog> findDogByCastrated(String castrated);
    Dog getDogById(UUID id);
    Dog updateDog(Dog dog, String status);
    List<Dog> getAllDogs();

}