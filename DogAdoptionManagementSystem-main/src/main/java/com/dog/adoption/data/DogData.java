package com.dog.adoption.data;
import lombok.Data;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class DogData {
    private Long microchip;
    private String name;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String breed;
    private String size;
    private String sterilised;
    private String castrated;
    private String note;
    private String adoptionStatus;
    private String image;
}



