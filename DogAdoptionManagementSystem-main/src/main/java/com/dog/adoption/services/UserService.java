package com.dog.adoption.services;

import com.dog.adoption.data.UserData;
import com.dog.adoption.models.User;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public interface UserService {
    boolean inputValidation(UserData request);

    User register(UserData request);

    User authentication(String email, String inpPassword);

    User getUserById(UUID  userId);


}

