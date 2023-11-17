package entities;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dog_vaccine")
public class dog_vaccine {

    @EmbeddedId
    private DogVaccineId dogVaccineId;

    @Embeddable
    public static class DogVaccineId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "dog_id", referencedColumnName = "id")
        private dogs dog;

        @ManyToOne
        @JoinColumn(name = "vaccine_name", referencedColumnName = "vaccine_name")
        private vaccines vaccine;

    }
}
