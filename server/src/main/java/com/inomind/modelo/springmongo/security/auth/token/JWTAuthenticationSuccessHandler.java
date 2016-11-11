package com.inomind.modelo.springmongo.security.auth.token;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inomind.modelo.springmongo.security.ConvertPrincipal;

public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private TokenAuthenticationService tokenService;
	
	private ConvertPrincipal convertPrincipal;

	public JWTAuthenticationSuccessHandler(TokenAuthenticationService tokenService, ConvertPrincipal convertPrincipal) {
		super();
		this.tokenService = tokenService;
		this.convertPrincipal = convertPrincipal;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		tokenService.addAuthentication(response, authentication);
		success(response, authentication);
	}
	
	private void success(HttpServletResponse response, Authentication authentication) throws IOException {
		JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());
		StringWriter writer = new StringWriter();
		JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);

		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			jsonGenerator.writeObject(convertPrincipal.convertPrincipal(principal));

			try {
				response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().print(writer.toString());
				response.getWriter().flush();
			} finally {
				writer.close();
			}
		}
	}

}
