package com.dog.adoption.models;
import lombok.Data;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table
public class Dog_Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;




    @ManyToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;
    @ManyToOne
    @JoinColumn(name = "vaccine_name", referencedColumnName = "vaccine_name")
    private Vaccine vaccine;


}