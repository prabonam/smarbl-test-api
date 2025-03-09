package com.smarbl.exception;

/**
 * Exception thrown when a post is not found.
 */
public class PostNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String message) {
        super(message);
    }
}
