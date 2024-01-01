package com.dog.adoption.mappers;

import com.dog.adoption.data.UserData;
import com.dog.adoption.models.User;
import com.dog.adoption.utils.PasswordUtils;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public class UserMapper {
    public static User mapDataEntity(UserData userData){

        User user = new User();
        user.setName(userData.getName());
        user.setSurname(userData.getSurname());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        user.setCity(userData.getCity());
        user.setAddress(userData.getAddress());
        user.setGender(userData.getGender());
        user.setRole(userData.getRole());
        user.setUsername(userData.getUsername());
        user.setPassword(PasswordUtils.hashPassword(userData.getPassword()));
        user.setPhoneNumber(userData.getPhoneNumber());
        user.setOib(null);
        user.setDateOfBirth(userData.getDateOfBirth());
        return user;
    }
}


