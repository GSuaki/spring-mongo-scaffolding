/**
 * 
 */
package com.inomind.modelo.springmongo.jwt;

import static com.inomind.modelo.springmongo.security.auth.AuthenticationHeaders.JWT_COOKIE;

import javax.servlet.http.HttpServletRequest;

import com.inomind.modelo.springmongo.utils.request.RequestDataExtractor;
import com.inomind.modelo.springmongo.utils.request.RequestDataExtractorBuilder;

/**
 * @author GSuaki
 *
 */
public class JWTRequestReader {
	
	private RequestDataExtractor exractor;
	
	public JWTRequestReader() {
		exractor = new RequestDataExtractorBuilder().forAllPossibilities().build();
	}
	
	public String read(HttpServletRequest request) throws NullPointerException {
		return this.getJWTFromRequest(request);
	}

	/**
	 * @param request
	 * @return
	 */
	private String getJWTFromRequest(HttpServletRequest request) {
		return exractor.extractData(request, JWT_COOKIE);
	}
}
