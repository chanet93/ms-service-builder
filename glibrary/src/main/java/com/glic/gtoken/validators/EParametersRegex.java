package com.glic.gtoken.validators;

import java.util.regex.Pattern;

import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EParametersRegex {

   PASSWORD(true, RegExpression.MEDIUM_LEVEL),
   CONFIRMPASSWORD(true, RegExpression.MEDIUM_LEVEL),
   OLDPASSWORD(true, RegExpression.MEDIUM_LEVEL),
   ENTITYID(false, RegExpression.ALPHA_HYPHEN),
   APPLICATIONID(false, RegExpression.ALPHA_HYPHEN),
   ID(false, RegExpression.ALPHA_HYPHEN),
   PARENTENTITYID(false, RegExpression.ALPHA_HYPHEN),
   PARENTAPPLICATIONID(false, RegExpression.ALPHA_HYPHEN),
   PARAMID(false, RegExpression.ALPHA_HYPHEN),
   ALPHANUMERICPARAMID(false, RegExpression.ALPHA_HYPHEN),
   NUMERPARAMID(false, RegExpression.ALPHA_HYPHEN),
   PUBLICKEY(false, RegExpression.PUBLIC_KEY),
   // default patterns
   DEFAULT(true, RegExpression.MAX_LEVEL),
   DEFAULT_MEDIUM(true, RegExpression.MEDIUM_LEVEL),
   DEFAULT_ALPHA_HYPHEN(false, RegExpression.ALPHA_HYPHEN);

   private static final Logger LOG = LoggerFactory.getLogger(EParametersRegex.class.getName());

   private final boolean whiteList;

   private final Pattern[] regex;

   EParametersRegex(boolean whiteList, Pattern... regex) {
      this.whiteList = whiteList;
      this.regex = regex;
   }

   public boolean isWhiteList() {
      return whiteList;
   }

   public Pattern[] getPatterns() {
      return regex;
   }

   public boolean isValid(String submittedValue) {
      String value = ESAPI.encoder().canonicalize(submittedValue);
      for (Pattern pattern : getPatterns()) {
         if (isWhiteList() && pattern.matcher(value).find()) {
            return false;
         } else if (!isWhiteList() && !pattern.matcher(value).find()) {
            return false;
         }
      }
      return true;
   }

   public static EParametersRegex getValidator(String param) {
      try {
         return EParametersRegex.valueOf(param.toUpperCase());
      } catch (IllegalArgumentException ex) {
         LOG.debug(String.format("Error getting Param: {0}", param), ex);
         return EParametersRegex.DEFAULT;
      }
   }

   /**
    * @author erwin
    */
   public static class RegExpression {

      static final Pattern[] MAX_LEVEL = { Pattern.compile("\\w*\\s*\\'\\s*or", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\'|(\\-\\-)|#", Pattern.DOTALL), Pattern.compile("<[^\\n]+>", Pattern.DOTALL) };

      static final Pattern[] MEDIUM_LEVEL = { Pattern.compile("\\w*\\s*\\'\\s*or", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<[^\\n]+>", Pattern.DOTALL) };

      static final Pattern ALPHA_HYPHEN = Pattern.compile("^[A-Za-z0-9-_]*$");

      static final Pattern PUBLIC_KEY = Pattern.compile("-{5}BEGIN PUBLIC KEY-{5}\\r?\\n[0-9A-Za-z+/\\r?\\n]+\\r?\\n-{5}END PUBLIC KEY-{5}");

   }

}
