package com.inomind.modelo.springmongo.utils.request;

import javax.servlet.http.HttpServletRequest;

/**
 * @author GSuaki
 */
public interface RequestDataExtractor {

	/**
	 * @param request
	 * @param key
	 * @return
	 * @throws NullPointerException
	 * 		when the arguments are null
	 * @throws IllegalArgumentException
	 * 		when 'key' is blank
	 */
	String extractData(HttpServletRequest request, String key) throws NullPointerException, IllegalArgumentException;
}