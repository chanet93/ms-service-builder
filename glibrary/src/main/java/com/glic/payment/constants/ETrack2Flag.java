package com.glic.payment.constants;

import static com.glic.util.CardUtils.getExpDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.glic.payment.model.BasicTrx;
import com.glic.util.TrackDataStandardUtil;
import com.glic.util.log.converter.impl.CardConverter;


public enum ETrack2Flag implements ITrack2Flag {

   NOT_PRESENT(0) {
      @Override
      public void updateCardInformation(BasicTrx msg) throws IllegalArgumentException {
         // MauricioC2: Terminal can send request without field <track2_flag> with value 1, but send <trackData> field with
         // track information. In this case, we assume that track data is in ISO7813 format.
         ISO7813.updateCardInformation(msg);
      }
   },
   ISO7813(1) {
      @Override
      public void updateCardInformation(BasicTrx msg) throws IllegalArgumentException {
         TrackDataStandardUtil.TrackData trackData = TrackDataStandardUtil.parseTrackData(msg.getTrack2());
         msg.setCardExpDate(getExpDate(trackData));
         msg.setPan(trackData.getCardNumber());
         msg.setTrack1(trackData.getTrack1());
         msg.setTrack2(trackData.getTrack2());
         msg.setMaskedTrack2(TrackDataStandardUtil.maskTrack2SensitiveData(trackData.getTrack2()));
         msg.setMaskedPan(converter.convert(msg.getPan()));
      }
   };



   private static final Map<Integer, ETrack2Flag> VALUES = new HashMap<>();

   private static final CardConverter converter = new CardConverter();

   private Integer value;

   static {
      for (ETrack2Flag e : ETrack2Flag.values()) {
         if (VALUES.put(e.getValue(), e) != null) {
            throw new IllegalArgumentException("ETrack2Flag duplicate id: " + e.getValue());
         }
      }
   }

   ETrack2Flag(Integer value) {
      this.value = value;
   }

   public Integer getValue() {
      return value;
   }

   public static ETrack2Flag getByValue(Integer id) {
      if (Objects.nonNull(id)) {
         return VALUES.get(id);
      }
      return VALUES.get(NOT_PRESENT.getValue());
   }

}
