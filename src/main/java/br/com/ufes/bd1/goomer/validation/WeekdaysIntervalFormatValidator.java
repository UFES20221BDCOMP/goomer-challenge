package br.com.ufes.bd1.goomer.validation;

import br.com.ufes.bd1.goomer.model.Weekday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WeekdaysIntervalFormatValidator implements ConstraintValidator<WeekdaysIntervalFormat, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String[] timeArray = s.split("-");
            Weekday.fromName(timeArray[0]);
            Weekday.fromName(timeArray[1]);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
