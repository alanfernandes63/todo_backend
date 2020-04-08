package com.alanfernandes.todos.exceptions;

@SuppressWarnings("serial")
public class ValidateTodoException extends RuntimeException{
	
	public ValidateTodoException(String message) {
		super(message);
	}

}
