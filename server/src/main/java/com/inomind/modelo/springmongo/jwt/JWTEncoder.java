/**
 * 
 */
package com.inomind.modelo.springmongo.jwt;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inomind.modelo.springmongo.security.DefaultUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author GSuaki
 *
 */
public class JWTEncoder {

	private Key apiKey;

	private SignatureAlgorithm algorithm;
	
	private ObjectMapper mapper;
	
	private DefaultUser user;
	
	/**
	 * 
	 */
	public JWTEncoder(String secret, DefaultUser user) {
		mapper  = new ObjectMapper();
		
		this.user = user;
		
		// The JWT signature algorithm we will be using to sign the token
		algorithm = SignatureAlgorithm.HS256;

		// Will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		apiKey = new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());
	}

	/**
	 * @param user
	 * @throws JsonProcessingException 
	 */
	public String encode() throws JsonProcessingException {
		JWTPayload payload = JWTPayload.build(this.user);
		
		return Jwts.builder()
				.setPayload(mapper.writeValueAsString(payload))
				.signWith(algorithm, apiKey).compact();
	}
}
