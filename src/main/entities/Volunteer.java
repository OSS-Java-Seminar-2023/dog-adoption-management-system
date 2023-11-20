package main.entities;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "volunteer")
public class Volunteer {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "stop_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stopTime;

}