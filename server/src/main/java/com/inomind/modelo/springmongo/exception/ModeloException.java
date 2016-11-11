package com.inomind.modelo.springmongo.exception;

public class ModeloException extends RuntimeException {

	private static final long serialVersionUID = -2735904669357698968L;

	private String message;
	private Integer statusCode;
	
	public String getMessage() {
		return message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}
	
	public ModeloException(String message, Integer statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

}
