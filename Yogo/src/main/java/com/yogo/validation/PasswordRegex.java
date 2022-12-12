package com.yogo.validation;

import com.yogo.message.MessageText;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Constraint(
        validatedBy = {PasswordValidator.class}
)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordRegex {
    String message() default "The password is not match regex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class PasswordValidator implements ConstraintValidator<PasswordRegex, String> {

    @Override
    public void initialize(PasswordRegex constraint) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && value.length() >= 8)
            return true;
        else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.PASSWORD_INVALID);
    }
}
