package com.inomind.modelo.springmongo.utils.request;

import static com.inomind.modelo.springmongo.security.auth.AuthenticationHeaders.JWT_HEADER;

import javax.servlet.http.HttpServletRequest;

/**
 * @author GSuaki
 */
public class HeaderDataExtractor implements RequestDataExtractor {

	@Override
	public String extractData(HttpServletRequest request, String key) throws NullPointerException, IllegalArgumentException {
		String auth = request.getHeader(JWT_HEADER);
		
		if (auth != null)
			return auth.substring(7);
		
		return null;
	}
}