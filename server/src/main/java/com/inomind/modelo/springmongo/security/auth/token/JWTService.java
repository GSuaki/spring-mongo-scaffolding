package com.inomind.modelo.springmongo.security.auth.token;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.inomind.modelo.springmongo.ModeloProperties;
import com.inomind.modelo.springmongo.jwt.JWTDecoder;
import com.inomind.modelo.springmongo.jwt.JWTEncoder;
import com.inomind.modelo.springmongo.jwt.JWTPayload;
import com.inomind.modelo.springmongo.security.DefaultUser;

@Service
public class JWTService {

	@Value("${" + ModeloProperties.SECRET + "}")
	private String secret;

	public DefaultUser getUser(String token) throws JsonParseException, JsonMappingException, IOException {
		JWTPayload payload = new JWTDecoder(secret, token).decode();
		return DefaultUser.build(payload);
	}

	public String generateToken(DefaultUser user) throws JsonProcessingException {
		return new JWTEncoder(secret, user).encode();
	}
}
