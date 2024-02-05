package com.dog.adoption.services.implementations;

import com.dog.adoption.data.DogData;
import com.dog.adoption.mappers.DogMapper;
import com.dog.adoption.models.Dog;
import com.dog.adoption.repository.DogRepository;
import com.dog.adoption.services.DogService;
import com.dog.adoption.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;

    @Override
    public boolean inputValidation(DogData request){
        return ValidationUtils.isStringNullOrEmpty(request.getName())||
                ValidationUtils.isStringNullOrEmpty(request.getGender())||
                ValidationUtils.isStringNullOrEmpty(request.getBreed())||
                ValidationUtils.isStringNullOrEmpty(request.getSize())||
                ValidationUtils.isStringNullOrEmpty(request.getCastrated())||
                ValidationUtils.isStringNullOrEmpty(request.getSterilised());
    }

    @Override
    public String saveDogImage(MultipartFile dogImage) throws IOException {
        String fileName = StringUtils.cleanPath(dogImage.getOriginalFilename());
        String uploadDir = "src/main/resources/static/img";

        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        File destFile = new File(uploadPath.getAbsolutePath() + File.separator + fileName);
        dogImage.transferTo(destFile);

        return dogImage.getOriginalFilename();
    }
    @Override
    public Dog addDog(DogData request, MultipartFile dogImage) {
        try {
            if (inputValidation(request)) {
                throw new RuntimeException("Invalid dog input");
            }
            request.setImage(saveDogImage(dogImage));
            Dog dog = DogMapper.mapDataEntity(request);
            dog.setAdoptionStatus("Available");
            return dogRepository.save(dog);
        } catch (IOException e) {
            throw new RuntimeException("Error saving dog image", e);
        }
    }
    @Override
    public List<Dog> listAllDogs(String size, String breed, String ageCategory, boolean castrated, boolean sterilised, String gender) {
        List<Dog> allDogs = dogRepository.findAll();

        return allDogs.stream()
                .filter(dog -> !"Adopted".equalsIgnoreCase(dog.getAdoptionStatus()))
                .filter(dog -> StringUtils.isEmpty(size) || size.equalsIgnoreCase(dog.getSize()))
                .filter(dog -> StringUtils.isEmpty(breed) || breed.equalsIgnoreCase(dog.getBreed()))
                .filter(dog -> filterByAgeCategory(dog, ageCategory))
                .filter(dog -> (castrated && "Yes".equalsIgnoreCase(dog.getCastrated())) || !castrated)
                .filter(dog -> (sterilised && "Yes".equalsIgnoreCase(dog.getSterilised())) || !sterilised)
                .filter(dog -> StringUtils.isEmpty(gender) || gender.equalsIgnoreCase(dog.getGender()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Dog> findDogByBreed(String breed) {
        return dogRepository.findByBreed(breed);
    }

    @Override
    public Optional<Dog> findDogBySize(String size) {
        return dogRepository.findBySize(size);
    }

    @Override
    public Optional<Dog> findDogBySterilised(String sterilised) {
        return dogRepository.findBySterilised(sterilised);
    }

    @Override
    public Optional<Dog> findDogByCastrated(String castrated) {
        return dogRepository.findByCastrated(castrated);
    }

    public int calculateAge(Date birthDate) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(birthDate);

        Calendar currentDate = Calendar.getInstance();

        int age = currentDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        return age;
    }

    private boolean filterByAgeCategory(Dog dog, String ageCategory) {
        Date dogBirthDate = dog.getDateOfBirth();
        int dogAge = calculateAge(dogBirthDate);

        if ("puppy".equalsIgnoreCase(ageCategory)) {
            return dogAge <= 1;
        } else if ("senior".equalsIgnoreCase(ageCategory)) {
            return dogAge >= 8;
        } else if ("adult".equalsIgnoreCase(ageCategory)) {
            return dogAge > 1 && dogAge < 8;
        }

        return true; // If ageCategory is not specified, include the dog in the result
    }

    @Override
    public Dog getDogById(UUID id) {
        return dogRepository.findById(id).orElse(null);
    }

    @Override
    public Dog updateDog(Dog dog, String status) {
        dog.setAdoptionStatus(status);
        return dogRepository.save(dog);
    }

    @Override
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }
}





