package com.pubint.projInit.exception;

public class MissingProjectException extends Exception {
	private static final long serialVersionUID = 1L;

	String message;

	public MissingProjectException() {
		super("Project not found exception");
	}

	public MissingProjectException(String message) {
		super(message);
	}

	public MissingProjectException(String message, Throwable parent) {
		super(message, parent);
	}

	public void printStackTrace() {
		System.out.println(getMessage());

		super.printStackTrace();
	}
}
