package com.smarbl.exception;

/**
 * Custom exception for handling user not found errors.
 */
public class UserNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
