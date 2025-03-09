package com.smarbl.exception;

public class InvalidCredentialsException extends RuntimeException {
	/**
	 * custom exception to handle invalid login attempts.
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException(String message) {
		super(message);
	}
}
