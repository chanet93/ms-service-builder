package com.glic.gtoken.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;


@Pattern.List({
      @Pattern(regexp = "[A-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-z0-9!#$%&'*+/=?^_`" + "{|}~-]+)*@(?:[A-z0-9](?:[A-z0-9-]*[A-z0-9])?\\.)+[A-z0-9](?:"
            + "[A-z0-9-]*[A-z0-9])?", message = "{com.glic.messages.emailValidator}") })
@Constraint(validatedBy = {})
@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidator {

   String message() default "{com.glic.messages.emailValidator}";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}
