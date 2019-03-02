package com.glic.gtoken.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author eetchart Annotation that is used to validate fields inside a pojo
 */

@Constraint(validatedBy = RequiredValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface Required {

   String OR = "OR";

   String DEPENDS = "DEPENDS";

   String RANGE = "RANGE";

   String REQUIRED = "REQUIRED";

   String DATE = "DATE";

   int min() default Integer.MAX_VALUE;

   int max() default Integer.MAX_VALUE;

   String mode() default DEPENDS;

   String field() default "";

   String[] fields() default {};

   String message() default "{Default message}";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};

   /**
    * @author erwin
    */
   @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   @interface List {

      Required[] value();
   }

}
