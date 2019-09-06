package com.incentives.piggyback.user.util.customAnnotations;

import com.incentives.piggyback.user.util.exceptionHandler.RoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
@Documented
public @interface Role {
    String message() default "Invalid Role passed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
