package com.inomind.modelo.springmongo.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WebException extends RuntimeException {

    private static final long serialVersionUID = 3406636182783807331L;

    private HttpStatus status;
    
    private String     message;

}
