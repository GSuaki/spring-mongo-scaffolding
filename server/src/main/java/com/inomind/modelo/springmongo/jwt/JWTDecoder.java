/**
 * 
 */
package com.inomind.modelo.springmongo.jwt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author GSuaki
 *
 */
public class JWTDecoder {

	@SuppressWarnings("unused")
	private Key apiKey;

	private SignatureAlgorithm algorithm;
	
	private ObjectMapper mapper;
	
	private String token;
	
	/**
	 * 
	 */
	public JWTDecoder(String secret, String token) {
		mapper  = new ObjectMapper();
		
		this.token = token;
		
		// The JWT signature algorithm we will be using to sign the token
		algorithm = SignatureAlgorithm.HS256;

		// Will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		apiKey = new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());
	}

	/**
	 * @param token
	 * @throws IOException 
	 */
	public JWTPayload decode() throws IOException {
		String[] tokens = token.split("\\.");

		if (tokens.length != 3) {
			throw new IllegalStateException("Token invalid size: " + tokens.length);
		}

		String encodedHeader  = tokens[0];
		String encodedPayload = tokens[1];

		JsonNode header = decodeAndParse(encodedHeader);
		if (!header.has("alg")) {
			throw new IllegalStateException("Invalid header format.");
		}

		JWTPayload payload = getPayload(encodedPayload);

		return payload;
	}
	
	private JWTPayload getPayload(String token) throws JsonParseException, JsonMappingException, IOException {

//		String json = Jwts.parser()
//				.setSigningKey(apiKey)
//				.parseClaimsJws(token)
//				.getBody().toString();
		
		String jsonString = new String(Base64Variants.MODIFIED_FOR_URL.decode(token), "UTF-8");
		
		JWTPayload node = mapper.readValue(jsonString, JWTPayload.class);

		return node;
	}
	
	public String parse(String json) throws UnsupportedEncodingException, IllegalArgumentException {
		String encode = Base64Variants.MODIFIED_FOR_URL.encode(json.getBytes());
		
		return new String(Base64Variants.MODIFIED_FOR_URL.decode(encode), "UTF-8");
	}
	
	private JsonNode decodeAndParse(String b64String) throws IOException {

		String jsonString = new String(Base64Variants.MODIFIED_FOR_URL.decode(b64String), "UTF-8");

		return mapper.readValue(jsonString, JsonNode.class);
	}
}
