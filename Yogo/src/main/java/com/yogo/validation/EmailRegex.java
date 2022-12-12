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
        validatedBy = {EmailValidator.class}
)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailRegex {
    String message() default "Email is not match regex";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class EmailValidator implements ConstraintValidator<EmailRegex, String> {
    EmailValidator() {
    }

    @Override
    public void initialize(EmailRegex constraint) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
        if (s != null && p.matcher(s).find())
            return true;
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.EMAIL_INVALID);
    }
}
