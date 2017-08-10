package com.seven.test.util.validation;

import lombok.Getter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * We’re rolling our own custom annotation instead of Hibernate’s @Email because Hibernate considers
 * the old intranet addresses format: myaddress@myserver as valid
 * (see <a href="http://stackoverflow.com/questions/4459474/hibernate-validator-email-accepts-askstackoverflow-as-valid">Stackoverflow</a> article), which is no good.
 */
public class EnsureEmailValidator implements ConstraintValidator<EnsureEmail, String> {
    @Getter
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public void initialize(EnsureEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return (validateEmail(email));
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
