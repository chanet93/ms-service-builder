package com.glic.util.log;

import static com.glic.util.log.FieldUtil.addField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.glic.util.log.converter.LogConverter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonLogger {

   public static void parseJson(String content, StringBuilder sb, int fieldId) {
      List<Pair<String, String>> myKeyValues = new ArrayList<>();
      if (StringUtils.isBlank(content)) {
         return;
      }

      JsonParser parser = new JsonParser();

      JsonObject jsonObject = parser.parse(content).getAsJsonObject();
      String previous = "";
      Boolean firstRelated = true;
      loadJson(myKeyValues, previous, jsonObject);
      for (Pair<String, String> pair : myKeyValues) {
         String fieldName = pair.getLeft();
         String value = pair.getRight();
         LogConverter converter = RequestParameterLog.getConverter(fieldName);
         if (Objects.nonNull(converter)) {
            final String fieldValue = Objects.isNull(value) ? "" : value;
            if (fieldName.startsWith("relatedTrxData.")) {
               if (firstRelated) {
                  firstRelated = false;
                  addField(sb, "relatedTrxData", "", fieldId++);
               }
               addField(sb, fieldName, converter.convert(fieldValue), fieldId++, " ");
            } else {
               addField(sb, fieldName, converter.convert(fieldValue), fieldId++);
            }
         }
      }
   }

   private static void loadJson(List<Pair<String, String>> myKeyValues, String previous, JsonElement element) {
      if (element.isJsonObject()) {
         JsonObject jsonObject = element.getAsJsonObject();
         for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String jsonKey = entry.getKey();
            JsonElement array = entry.getValue();
            loadJson(myKeyValues, previous + "." + jsonKey, array);
         }
      } else if (element.isJsonArray()) {
         JsonArray jsonArray = element.getAsJsonArray();
         for (JsonElement childElement : jsonArray) {
            loadJson(myKeyValues, previous, childElement);
         }
      } else {
         String key = previous;
         key = key.substring(1);
         if (!element.isJsonNull()) {
            myKeyValues.add(Pair.of(key, element.getAsString()));
         }
      }
   }

}
