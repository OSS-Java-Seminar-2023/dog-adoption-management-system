package com.dog.adoption.services;
import lombok.AllArgsConstructor;
import com.dog.adoption.models.Vaccine;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public interface VaccineService {
    List<Vaccine> listAllVaccines();

}