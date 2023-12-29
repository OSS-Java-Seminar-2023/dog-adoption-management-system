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
import java.util.List;

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
        String uploadDir = "src/main/resources/pictures"; // Specify your upload directory

        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        File destFile = new File(uploadPath.getAbsolutePath() + File.separator + fileName);
        dogImage.transferTo(destFile);

        return dogImage.getOriginalFilename();
    }
    @Override
    public Dog dogRegister(DogData request, MultipartFile dogImage) {
        try {
            if (inputValidation(request)) {
                throw new RuntimeException("Invalid dog input");
            }
            request.setImage(saveDogImage(dogImage));
            Dog dog = DogMapper.mapDataEntity(request);
            return dogRepository.save(dog);
        } catch (IOException e) {
            throw new RuntimeException("Error saving dog image", e);
        }
    }

    @Override
    public List<Dog> listAllDogs(){
        return dogRepository.findAll();
    }


}





