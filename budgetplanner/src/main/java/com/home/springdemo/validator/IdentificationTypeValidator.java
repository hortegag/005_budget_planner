package com.home.springdemo.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.home.budgetplanner.entity.IdentificationType;


public class IdentificationTypeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
       return clazz.isAssignableFrom(IdentificationType.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
       IdentificationType identificationType = (IdentificationType) target;
        
       
       
       ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required", new Object[] { "Identification name" });
       ValidationUtils.rejectIfEmpty(errors, "mnemonic", "error.required");
    }
 }