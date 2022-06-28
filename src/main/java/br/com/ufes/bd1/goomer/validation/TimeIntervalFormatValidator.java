package br.com.ufes.bd1.goomer.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeIntervalFormatValidator implements ConstraintValidator<TimeIntervalFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            String[] timeArray = value.split("-");
            LocalTime timeStart = LocalTime.parse(timeArray[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime timeEnd = LocalTime.parse(timeArray[1], DateTimeFormatter.ofPattern("HH:mm"));

            if (timeStart.isBefore(timeEnd)) {
                if (timeStart.plusMinutes(15).isAfter(timeEnd)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
