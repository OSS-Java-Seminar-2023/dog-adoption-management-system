package com.dog.adoption.services.implementations;
import com.dog.adoption.models.Vaccine;
import com.dog.adoption.services.VaccineService;
import com.dog.adoption.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@AllArgsConstructor
public class VaccineServiceImpl implements VaccineService{

    private final VaccineRepository vaccineRepository;
    @Override
    public List<Vaccine> listAllVaccines(){
        return vaccineRepository.findAll();
    }
}
