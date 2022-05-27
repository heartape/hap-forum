package com.heartape.hap.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        int PHONE_LENGTH = 11;
        if (s.length() != PHONE_LENGTH) {
            return false;
        }
        Pattern pattern = Pattern.compile("1[3|4|5|7|8][0-9]\\d{8}");
        return pattern.matcher(s).matches();
    }
}
