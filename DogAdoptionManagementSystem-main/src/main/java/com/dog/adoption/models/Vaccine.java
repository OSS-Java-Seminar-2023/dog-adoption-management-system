package com.dog.adoption.models;
import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
@Table(name="vaccine")
public class Vaccine {

    @Id
    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

}