package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException {

	// Default constructor
	public ResourceNotFoundException(String message) {
		super(message);
	}

	// You can add other constructors if needed
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
