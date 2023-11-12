package entities;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@Entity
public class dog_vaccine {
    @Id
    @GeneratedValue
    private UUID id;

}
