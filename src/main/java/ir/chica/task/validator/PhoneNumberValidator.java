package ir.chica.task.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber != null &&
                phoneNumber.matches("(\\+98|098|09)[0-9]") &&
                phoneNumber.length() > 8 &&
                phoneNumber.length() < 14;
    }
}

