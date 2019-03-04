package com.glic.hsm.payshield.exception;

public class HsmException extends Exception {

	private static final long serialVersionUID = 5725800058725431386L;

	public HsmException() {
		super();
	}

	public HsmException(String message) {
		super(message);
	}

	public HsmException(Throwable cause) {
		super(cause);
	}

	public HsmException(String message, Throwable cause) {
		super(message, cause);
	}

	public HsmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}