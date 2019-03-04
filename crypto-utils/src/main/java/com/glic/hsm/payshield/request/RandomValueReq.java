package com.glic.hsm.payshield.request;


/**
 * Random value command
 * 
 * @date 15/10/2014
 * @author ErnestoQ1
 */
public class RandomValueReq extends PayshieldCommandReq {

	private int size;
	
	public RandomValueReq() {
		setCommandCode(PayshieldCommandReq.RANDOM_VALUE_CMD);
	}
	
	public RandomValueReq(int size) {
		setCommandCode(PayshieldCommandReq.RANDOM_VALUE_CMD);
		this.setSize(size);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
