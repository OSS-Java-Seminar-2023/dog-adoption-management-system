package main.entities;
import javax.persistence.*;

@Entity
@Table(name = "vaccine")
public class Vaccine {

    @Id
    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

}