package com.dog.adoption.models;
import lombok.Data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;

    @Column(name = "date_of_contract", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfContract;

    @Setter
    @Getter
    @Column(name = "status", nullable = false)
    private String status;

}