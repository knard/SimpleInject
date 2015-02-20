package org.knard.SimpleInject;

public class InvocationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6998263041810329334L;

	public InvocationException() {
		super();
	}

	public InvocationException(String message) {
		super(message);
	}

	public InvocationException(Throwable cause) {
		super(cause);
	}

	public InvocationException(String message, Throwable cause) {
		super(message, cause);
	}
}
