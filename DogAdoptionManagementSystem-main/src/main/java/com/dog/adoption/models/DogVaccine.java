package com.dog.adoption.models;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@Table(name="dog_vaccine")
public class DogVaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;

    @ManyToOne
    @Setter
    @Getter
    @JoinColumn(name = "vaccine_name", referencedColumnName = "vaccine_name")
    private Vaccine vaccine;

}