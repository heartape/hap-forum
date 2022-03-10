package com.heartape.hap.api.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, Collection<String>> {

    @Override
    public boolean isValid(Collection<String> collection, ConstraintValidatorContext constraintValidatorContext) {
        for (String phone : collection) {
            Pattern pattern = Pattern.compile("1[3|4|5|7|8][0-9]\\d{8}");
            if (!pattern.matcher(phone).matches()) {
                return false;
            }
        }
        return true;
    }
}
