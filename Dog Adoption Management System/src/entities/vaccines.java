package entities;
import javax.persistence.*;

@Entity
@Table(name = "Vaccines")
public class vaccines {

    @Id
    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

}