package com.dog.adoption.models;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table
public class Vaccine {

    @Id
    @GeneratedValue
    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

}