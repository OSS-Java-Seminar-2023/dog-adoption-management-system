package com.dog.adoption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.dog.adoption.models")
public class DogAdoptionManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(DogAdoptionManagementSystem.class, args);
    }
}