/**
 * 
 */
package com.inomind.modelo.springmongo.utils.request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author GSuaki
 *
 */
public class CookieDataExtractor implements RequestDataExtractor {
	
	@Override
	public String extractData(HttpServletRequest request, String key) throws NullPointerException, IllegalArgumentException {
		
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}

		return null;
	}
}
