package com.inomind.modelo.springmongo.security.auth.token;

import static com.inomind.modelo.springmongo.security.auth.AuthenticationHeaders.JWT_COOKIE;
import static com.inomind.modelo.springmongo.security.auth.AuthenticationHeaders.JWT_HEADER;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.util.CookieGenerator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.inomind.modelo.springmongo.ModeloProperties;
import com.inomind.modelo.springmongo.jwt.JWTRequestReader;
import com.inomind.modelo.springmongo.security.DefaultUser;
import com.inomind.modelo.springmongo.security.auth.ModeloAuthentication;

@Service
public class TokenAuthenticationService {

	@Value("${" + ModeloProperties.IS_HTTPS + "}")
	private Boolean https;
	
	@Value("${" + ModeloProperties.DOMAIN + "}")
	private String domain;
	
	@Autowired
	private JWTService jwtService;
	
	public void addAuthentication(HttpServletResponse response, Authentication authentication) throws JsonProcessingException {
		
		final DefaultUser user = (DefaultUser) authentication.getPrincipal();
		final String token = jwtService.generateToken(user);
		
		CookieGenerator cg = new CookieGenerator();
        cg.setCookiePath("/");
		cg.setCookieHttpOnly(true);
		cg.setCookieDomain(this.domain);
		cg.setCookieSecure(this.https);
		cg.setCookieMaxAge(60 * 60 * 3600);
		cg.setCookieName(JWT_COOKIE);
		
		cg.addCookie(response, token);
		response.addHeader(JWT_HEADER, new StringBuilder("Bearer ").append(token).toString());
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String jwt = new JWTRequestReader().read(request);
		
		if (jwt != null) {
			DefaultUser user = null;
			
			try {
				user = jwtService.getUser(jwt);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (user != null) {
				return new ModeloAuthentication(user);
			}
		}
		
		return null;
	}

	/**
	 * @param authentication 
	 * @param response 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * 
	 */
	public void deleteToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws JsonParseException, JsonMappingException, IOException {
		response.setHeader(JWT_COOKIE, null);
	}
}
