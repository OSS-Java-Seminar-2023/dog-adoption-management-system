package com.dog.adoption.models;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="waiting_list")
public class WaitingList {

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

    @Column(name = "date_of_application", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfApplication;

}