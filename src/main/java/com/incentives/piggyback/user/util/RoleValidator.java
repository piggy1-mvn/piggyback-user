package com.incentives.piggyback.user.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;


public class RoleValidator  implements ConstraintValidator<Role, String> {
    List<String> rolesAllowed =  Roles.getAllRoles();
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return rolesAllowed.contains(value);

    }
}
