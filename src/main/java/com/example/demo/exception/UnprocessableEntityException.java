package com.example.demo.exception;

public class UnprocessableEntityException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	public UnprocessableEntityException(String message) {
		super(message);
	}
	
	

}
