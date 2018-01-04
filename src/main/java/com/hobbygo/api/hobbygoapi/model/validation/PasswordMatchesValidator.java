package com.hobbygo.api.hobbygoapi.model.validation;

import com.hobbygo.api.hobbygoapi.model.validation.interfaces.PasswordMatches;
import com.hobbygo.api.hobbygoapi.restapi.dto.CreateUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CreateUserDto user = (CreateUserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
