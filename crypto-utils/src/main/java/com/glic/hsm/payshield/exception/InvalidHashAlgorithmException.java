package com.glic.hsm.payshield.exception;

public class InvalidHashAlgorithmException extends HsmException {

	private static final long serialVersionUID = 3354713139478490816L;

	public InvalidHashAlgorithmException() {
        super();
    }

    public InvalidHashAlgorithmException(String message) {
        super(message);
    }

    public InvalidHashAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHashAlgorithmException(Throwable cause) {
        super(cause);
    }
}
