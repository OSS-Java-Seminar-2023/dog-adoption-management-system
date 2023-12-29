package com.dog.adoption.services;
import lombok.AllArgsConstructor;
import com.dog.adoption.data.DogData;
import com.dog.adoption.models.Dog;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
public interface DogService {
    boolean inputValidation(DogData request);
    String saveDogImage(MultipartFile dogImage) throws IOException;
    Dog dogRegister(DogData request,  MultipartFile dogImage);

    List<Dog> listAllDogs();
}