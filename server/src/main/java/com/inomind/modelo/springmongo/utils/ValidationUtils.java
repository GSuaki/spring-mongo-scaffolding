package com.inomind.modelo.springmongo.utils;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

/**
 * @author GSuaki
 */
public abstract class ValidationUtils {

	public static void validateNotBlank(String param, String paramName) throws NullPointerException, IllegalArgumentException {
		Validate.notBlank(param, "'"+paramName+"' cannot be null or blank !");
	}
	
	public static void validateNotNull(Object param, String paramName) throws NullPointerException {
		Validate.notNull(param, "'"+paramName+"' cannot be null !");
	}
	
	public static void validateNotEmpty(Collection<?> param, String paramName) throws NullPointerException, IllegalArgumentException {
		Validate.notEmpty(param, "'"+paramName+"' cannot be null !");
	}
}