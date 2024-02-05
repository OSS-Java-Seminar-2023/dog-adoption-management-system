package com.dog.adoption.models;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "oib")
    private Long oib;

    @Column(name = "role", nullable = false)
    private String role;

    @Setter
    @Getter
    @Column(name = "email", nullable = false)
    private String email;

    @Setter
    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Setter
    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(name = "surname", nullable = false)
    private String surname;

    @Setter
    @Getter
    @Column(name = "gender", nullable = false)
    private String gender;

    @Setter
    @Getter
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Setter
    @Getter
    @Column(name = "city", nullable = false)
    private String city;

    @Setter
    @Getter
    @Column(name = "address", nullable = false)
    private String address;

    public User(UUID id) {
        this.id = id;
    }

}

