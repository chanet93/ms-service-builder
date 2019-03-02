package com.glic.gtoken.validators;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ParametersValidatorImplementation implements ConstraintValidator<ParametersValidator, Object> {

   private EParametersRegex validator;

   @Override
   public void initialize(ParametersValidator annotatedParameter) {
      this.validator = annotatedParameter.parameter();
   }

   @Override
   public boolean isValid(Object submittedValue, ConstraintValidatorContext cvc) {
      if (Objects.isNull(submittedValue)) {
         return true;
      }
      return validator.isValid(String.valueOf(submittedValue));
   }
}
