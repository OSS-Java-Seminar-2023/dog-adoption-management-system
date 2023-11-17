package entities;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private users user;

    @ManyToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private dogs dog;

    @Column(name = "date_of_contract", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfContract;

    @Column(name = "status", nullable = false)
    private String status;

}
