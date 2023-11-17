package entities;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "dogs")
public class dogs {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "microchip", nullable = false)
    private Long microchip;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "date_of_birth", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "sterilised", nullable = false)
    private String sterilised;

    @Column(name = "castrated", nullable = false)
    private String castrated;

    @Column(name = "note", length = 200)
    private String note;

    @Column(name = "adoption_status", nullable = false)
    private String adoptionStatus;

    @Column(name = "image", nullable = false)
    private String image;

}
