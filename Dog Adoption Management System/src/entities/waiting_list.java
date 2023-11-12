package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Waiting_List")
public class waiting_list {

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

    @Column(name = "date_of_application", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfApplication;

}