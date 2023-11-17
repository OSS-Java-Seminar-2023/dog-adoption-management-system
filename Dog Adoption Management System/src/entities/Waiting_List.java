package entities;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "waiting_list")
public class Waiting_List {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dogs dog;

    @Column(name = "date_of_application", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfApplication;

}
