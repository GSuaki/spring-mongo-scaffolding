/**
 * 
 */
package com.inomind.modelo.springmongo.security.auth.token;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author GSuaki
 *
 */
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private final TokenAuthenticationService tokenAuthService;
	
	/**
	 * @param tokenAuthenticationService
	 */
	public JWTLogoutSuccessHandler(String tokenSecret, TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthService = tokenAuthenticationService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		this.tokenAuthService.deleteToken(request, response, authentication);
	}

}
