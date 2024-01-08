package com.dog.adoption.mappers;
import com.dog.adoption.data.UserData;
import com.dog.adoption.data.VaccineData;
import com.dog.adoption.models.User;
import com.dog.adoption.models.Vaccine;
import com.dog.adoption.utils.PasswordUtils;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
@Mapper(componentModel = "spring")
@Component
public class VaccineMapper {
    public static Vaccine mapDataEntity(VaccineData vaccineData){

        Vaccine vaccine = new Vaccine();
        vaccine.setVaccineName(vaccineData.getVaccineName());
        return vaccine;
    }
}
