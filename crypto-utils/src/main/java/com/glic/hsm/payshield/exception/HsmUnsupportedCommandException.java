package com.glic.hsm.payshield.exception;

public class HsmUnsupportedCommandException extends HsmException {

	private static final long serialVersionUID = 5109661127666640250L;

	public HsmUnsupportedCommandException() {
        super();
    }

    public HsmUnsupportedCommandException(String message) {
        super(message);
    }

    public HsmUnsupportedCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public HsmUnsupportedCommandException(Throwable cause) {
        super(cause);
    }
}
