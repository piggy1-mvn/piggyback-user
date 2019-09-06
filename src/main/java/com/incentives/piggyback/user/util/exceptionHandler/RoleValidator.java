package com.incentives.piggyback.user.util.exceptionHandler;

import com.incentives.piggyback.user.util.constants.Roles;
import com.incentives.piggyback.user.util.customAnnotations.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class RoleValidator  implements ConstraintValidator<Role, String> {
    List<String> rolesAllowed =  Roles.getAllRoles();
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return rolesAllowed.contains(value);
    }
}
