package com.bootcamp.demo.assignment.exception;

public class DuplicateRecordException extends RuntimeException{

	public DuplicateRecordException() {
	}

	public DuplicateRecordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DuplicateRecordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DuplicateRecordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DuplicateRecordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
