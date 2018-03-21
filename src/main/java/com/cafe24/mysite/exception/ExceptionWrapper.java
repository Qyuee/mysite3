package com.cafe24.mysite.exception;

public class ExceptionWrapper extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ExceptionWrapper(String className) {
		super(className+"Exception Occurs!!!");
	}
	
}
