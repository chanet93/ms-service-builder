package com.glic.gtoken.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Constraint(validatedBy = ParametersValidatorImplementation.class)
public @interface ParametersValidator {

   String message() default "{com.verifone.messages.parameterValidator}";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};

   EParametersRegex parameter() default EParametersRegex.DEFAULT;

}
