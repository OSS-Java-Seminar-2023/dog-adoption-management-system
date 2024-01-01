package com.dog.adoption.data;
import lombok.Data;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class UserData {
    private Long oib;
    private String role;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private String surname;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String city;
    private String address;
}





