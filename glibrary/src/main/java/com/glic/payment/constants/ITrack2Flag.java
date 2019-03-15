package com.glic.payment.constants;

import com.glic.payment.model.BasicTrx;


@FunctionalInterface
public interface ITrack2Flag {

   void updateCardInformation(BasicTrx msg) throws IllegalArgumentException;

}
