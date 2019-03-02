package com.glic.util.log;

/**
 * Created by Jorge_L2 on 21/12/2016.
 */
public class FieldUtil {

   public static void addField(StringBuilder body, String fieldName, Object value, int fieldId) {
      addField(body, fieldName, value, fieldId, "");
   }

   public static void addField(StringBuilder body, String fieldName, Object value, int fieldId, String tabSeparator) {
      body.append(tabSeparator.equals("") ? tabSeparator : "  " + tabSeparator);
      body.append("  [").append(fieldId).append("] ");
      body.append(fieldName).append('=');
      body.append("'").append(value).append("'\n");
   }

   public static void addArraySimpleValue(StringBuilder body, Object value, String tabSeparator) {
      body.append(tabSeparator.equals("") ? tabSeparator : "  " + tabSeparator);
      body.append(value + ",\n");
   }

   public static void addObjectHeader(StringBuilder body, String fieldName, int fieldId, String tabSeparator, String objIndicator) {
      body.append(tabSeparator.equals("") ? tabSeparator : "  " + tabSeparator);
      body.append("  [").append(fieldId).append("] ");
      body.append(fieldName).append("=" + objIndicator);
      body.append("\n");
   }

   public static void addSimpleHeader(StringBuilder body, String tabSeparator, String objIndicator) {
      body.append(tabSeparator.equals("") ? tabSeparator : "  " + tabSeparator);
      body.append("  " + objIndicator);
      body.append("\n");
   }

   public static void addObjectHeaderEnd(StringBuilder body, String tabSeparator, String objIndicator) {
      body.append(tabSeparator.equals("") ? tabSeparator : "  " + tabSeparator);
      body.append("  " + objIndicator);
      body.append("\n");
   }

   public static void addSimpleHeaderEnd(StringBuilder body, String tabSeparator, String objIndicator) {
      body.append(tabSeparator.equals("") ? tabSeparator : "  " + tabSeparator);
      body.append("  " + objIndicator);
      body.append("\n");
   }

}
