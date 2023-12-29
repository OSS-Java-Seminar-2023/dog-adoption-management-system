package com.dog.adoption.utils;
import java.util.Date;
import java.util.Calendar;
public class ValidationUtils {
    public static boolean isStringNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    public static boolean isLongNullOrEmpty(Long value){return value == null;}

    public static boolean isAgeInvalid(Date dateOfBirth) {
        if (dateOfBirth == null) {
            return true; // Date of birth is not provided
        }

        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);

        Calendar currentDate = Calendar.getInstance();

        int age = currentDate.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        // Adjust age for leap years
        if (currentDate.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age < 18 || age > 120;
    }
}
