package com.seven.test.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

public class EnsureNumberValidator implements ConstraintValidator<EnsureNumber, Object> {

    //ensureNumber.decimal() ? "-?[0-9][0-9\\.\\,]*" : "-?[0-9]+";
    private static final String NUMBER_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    @Override
    public void initialize(EnsureNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (isNull(value)) {
            return false;
        }

        // Initialize it.
        String data = String.valueOf(value);
        return data.matches(NUMBER_REGEX);
    }
}
