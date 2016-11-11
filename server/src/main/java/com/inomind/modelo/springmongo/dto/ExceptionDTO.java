package com.inomind.modelo.springmongo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionDTO {

	private String message;

	private String stackTrace;

	public ExceptionDTO(String message, String stackTrace) {
		this.message = message;
		this.stackTrace = stackTrace;
	}

}
