package com.dog.adoption.services.implementations;

import com.dog.adoption.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.dog.adoption.services.UserService;
import com.dog.adoption.data.UserData;
import com.dog.adoption.mappers.UserMapper;
import com.dog.adoption.models.User;
import com.dog.adoption.utils.PasswordUtils;
import com.dog.adoption.utils.ValidationUtils;


import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final  UserRepository userRepository;
    @Override
    public boolean inputValidation(UserData request){
        return ValidationUtils.isStringNullOrEmpty(request.getName())||
                ValidationUtils.isStringNullOrEmpty(request.getSurname())||
                ValidationUtils.isStringNullOrEmpty(request.getName())||
                ValidationUtils.isStringNullOrEmpty(request.getCity())||
                ValidationUtils.isStringNullOrEmpty(request.getEmail())||
                ValidationUtils.isStringNullOrEmpty(request.getAddress())||
                ValidationUtils.isStringNullOrEmpty(request.getGender())||
                ValidationUtils.isAgeInvalid(request.getDateOfBirth())||
                ValidationUtils.isStringNullOrEmpty(request.getPassword());
    }
    @Override
    public User register(UserData request) {
        if (inputValidation(request)) {
            throw new RuntimeException("Invalid user input");
        }


        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Duplicate email");
        }

        request.setRole("User");
        User user = UserMapper.mapDataEntity(request);
        return userRepository.save(user);
    }
    @Override
    public User authentication(String email, String inpPassword){
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (PasswordUtils.verifyPassword(inpPassword, user.getPassword())) {
                        return user;
                    } else {
                        throw new RuntimeException("Wrong password");
                    }
                })
                .orElseThrow(() -> new RuntimeException("Authentication failed"));

    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User adminCreateStaff(UserData request) {
        if (inputValidation(request)) {
            throw new RuntimeException("Invalid user input");
        }


        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Duplicate email");
        }

        request.setRole("Staff");
        User user = UserMapper.mapDataEntity(request);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}