package com.dog.adoption.mappers;

import com.dog.adoption.data.DogData;
import com.dog.adoption.models.Dog;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public class DogMapper {
    public static Dog mapDataEntity(DogData dogData){

        Dog dog = new Dog();
        dog.setName(dogData.getName());
        dog.setMicrochip(dogData.getMicrochip());
        dog.setGender(dogData.getGender());
        dog.setDateOfBirth(dogData.getDateOfBirth());
        dog.setBreed(dogData.getBreed());
        dog.setCastrated(dogData.getCastrated());
        dog.setAdoptionStatus(dogData.getAdoptionStatus());
        dog.setImage(dogData.getImage());
        dog.setNote(dogData.getNote());
        dog.setSize(dogData.getSize());
        dog.setSterilised(dogData.getSterilised());
        return dog;
    }
}

