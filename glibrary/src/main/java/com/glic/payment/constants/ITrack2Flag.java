package com.glic.payment.constants;

import com.glic.payment.model.BasicTrx;

/**
 * @author MauricioC2
 * @since 20/09/2016
 */
@FunctionalInterface
public interface ITrack2Flag {

   void updateCardInformation(BasicTrx msg) throws IllegalArgumentException;

}
