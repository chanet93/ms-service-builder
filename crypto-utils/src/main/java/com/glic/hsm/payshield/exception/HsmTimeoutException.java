package com.glic.hsm.payshield.exception;

public class HsmTimeoutException extends HsmException {

	private static final long serialVersionUID = 5109661127666640250L;

	public HsmTimeoutException() {
        super();
    }

    public HsmTimeoutException(String message) {
        super(message);
    }

    public HsmTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public HsmTimeoutException(Throwable cause) {
        super(cause);
    }
}
