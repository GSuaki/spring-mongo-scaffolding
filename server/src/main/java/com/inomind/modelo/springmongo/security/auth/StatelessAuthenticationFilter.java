package com.inomind.modelo.springmongo.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import com.inomind.modelo.springmongo.security.UserUtils;
import com.inomind.modelo.springmongo.security.auth.token.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	final RequestMatcher ignore;
	
	final TokenAuthenticationService authenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService, RequestMatcher matcher) {
		this.ignore = matcher;
		this.authenticationService = authenticationService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!ignore.matches(request)) {
			
			Authentication authentication = authenticationService.getAuthentication(request);
			
			UserUtils.setUserAuthenticated(authentication);
		} 

		filterChain.doFilter(request, response);

//		UserUtils.setUserAuthenticated(null);
	}
	
}
