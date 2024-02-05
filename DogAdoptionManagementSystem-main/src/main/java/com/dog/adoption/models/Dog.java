package com.dog.adoption.models;
import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "microchip", nullable = false)
    private Long microchip;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "sterilised", nullable = false)
    private String sterilised;

    @Column(name = "castrated", nullable = false)
    private String castrated;

    @Column(name = "note", length = 200)
    private String note;

    @Setter
    @Getter
    @Column(name = "adoption_status", nullable = false)
    private String adoptionStatus;

    @Column(name = "image", nullable = false)
    private String image;

}