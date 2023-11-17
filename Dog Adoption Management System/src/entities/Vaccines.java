package entities;
import javax.persistence.*;

@Entity
@Table(name = "vaccines")
public class Vaccines {

    @Id
    @Column(name = "vaccine_name", nullable = false)
    private String vaccineName;

}
