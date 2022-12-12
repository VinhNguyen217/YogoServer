package com.yogo.validation;

import com.yogo.message.MessageText;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

@Documented
@Target({ElementType.FIELD})
@Constraint(
        validatedBy = {PhoneValidator.class}
)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneRegex {
    String message() default "The phone is not match regex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class PhoneValidator implements ConstraintValidator<PhoneRegex, String> {
    PhoneValidator() {
    }

    @Override
    public void initialize(PhoneRegex constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern p = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b", Pattern.CASE_INSENSITIVE);
        if (s != null && p.matcher(s).find())
            return true;
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.PHONE_INVALID);
    }
}
