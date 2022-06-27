package br.com.ufes.bd1.goomer.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeIntervalFormatValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeIntervalFormat {
    String message() default "Invalid format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

