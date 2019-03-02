package com.glic.gtoken.validators;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author eetchart Validator implementation for required annotation
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {

   private String mode;

   private String[] fields;

   private String field;

   private String defaultMessage;

   private int min;

   private int max;

   @Override
   public void initialize(Required annotation) {
      field = annotation.field();
      fields = annotation.fields();
      mode = annotation.mode();
      defaultMessage = annotation.message();
      min = annotation.min();
      max = annotation.max();
   }

   @Override
   public boolean isValid(Object value, ConstraintValidatorContext context) {
      Boolean result = Boolean.TRUE;

      if (StringUtils.equals(mode, Required.DEPENDS)) {
         result = validateByDepends(value);
      } else if (StringUtils.equals(mode, Required.OR)) {
         result = validateByOR(value);
      } else if (StringUtils.equals(mode, Required.RANGE)) {
         result = validateByRange(value);
      } else if (StringUtils.equals(mode, Required.REQUIRED)) {
         result = validateRequired(value);
      } else if (StringUtils.equals(mode, Required.DATE)) {
         result = validateDate(value);
      }

      if (!result) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(defaultMessage).addConstraintViolation();
      }

      return result;
   }

   private Boolean validateDate(Object object) {
      Boolean result = Boolean.FALSE;

      String[] newFields = (String[]) ArrayUtils.add(fields, field);

      if (!ArrayUtils.isEmpty(newFields)) {

         for (String value : newFields) {
            try {
               Object obj = PropertyUtils.getSimpleProperty(object, value);

               if (obj == null) {
                  return true;
               }

               if (obj instanceof Date) {
                  result = !((Date) obj).before(new Date());
               }

               if (obj instanceof GregorianCalendar) {
                  result = !((Calendar) obj).before(new Date());
               }

               if (result == Boolean.FALSE) {
                  defaultMessage = "The date cannot be in the past for field " + value;
                  return Boolean.FALSE;
               }

            } catch (Exception e) {
               defaultMessage = e.getMessage();
            }
         }
      }

      return result;
   }

   /**
    * @param object
    * @return Validates strictrly that a value is present.
    */
   private Boolean validateRequired(Object object) {
      Boolean result = Boolean.TRUE;

      String[] newFields = (String[]) ArrayUtils.add(fields, field);

      if (!ArrayUtils.isEmpty(newFields)) {

         for (String value : newFields) {

            try {
               Object obj = BeanUtils.getProperty(object, value);

               if (obj == null) {
                  result = Boolean.FALSE;
                  defaultMessage = "Field " + value + " is required";
               }

            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
               defaultMessage = e.getMessage();
               result = Boolean.FALSE;

            }
         }
      }

      return result;
   }

   /**
    * @param bean
    * @return Validates that if some value exists , others must be present.
    */
   private Boolean validateByDepends(Object bean) {
      Boolean result = Boolean.TRUE;

      if (checkField(bean, field)) {

         for (String value : fields) {
            try {
               Object object = BeanUtils.getProperty(bean, value);
               if (object == null) {
                  defaultMessage = "If field " + field + " is present " + value + " must be set";
                  result = Boolean.FALSE;
               }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
               defaultMessage = e.getMessage();
               result = Boolean.FALSE;
            }
         }
      }
      return result;

   }

   private boolean checkField(Object bean, String value) {
      try {
         Object object = BeanUtils.getProperty(bean, value);
         if (object == null) {
            return Boolean.FALSE;
         }
      } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
         defaultMessage = e.getMessage();
         return Boolean.FALSE;
      }
      return Boolean.TRUE;
   }

   /**
    * @param bean
    * @return Validate that some of the passed values are present
    */
   private Boolean validateByOR(Object bean) {
      Boolean result = Boolean.TRUE;

      if (ArrayUtils.isNotEmpty(fields)) {

         for (String value : fields) {

            try {
               Object object = BeanUtils.getProperty(bean, value);
               if (StringUtils.isNotEmpty((String) object)) {
                  return Boolean.TRUE;
               }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
               defaultMessage = e.getMessage();
               result = Boolean.FALSE;
            }

            defaultMessage = "At least one field is required " + Arrays.toString(fields);
            result = Boolean.FALSE;
         }
      }
      return result;
   }

   private Boolean validateByRange(Object bean) {

      Boolean result = Boolean.TRUE;

      if (checkField(bean, field)) {
         try {
            String object = BeanUtils.getProperty(bean, field);

            if (object != null) {
               try {
                  Integer integer = Integer.valueOf(object);
                  result = integer >= min && integer <= max;
                  if (!result) {
                     defaultMessage = "Field " + field + " must be between " + min + " and " + max;
                  }
               } catch (Exception e) {
                  defaultMessage = "Field " + field + " is not a number";
                  result = Boolean.FALSE;
               }
            }
         } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            defaultMessage = e.getMessage();
            result = Boolean.FALSE;
         }
      }

      return result;
   }

}
