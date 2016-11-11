package com.inomind.modelo.springmongo.controller;

import static com.inomind.modelo.springmongo.constantes.ControllerConstants.ME;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.inomind.modelo.springmongo.ApplicationTest;
import com.inomind.modelo.springmongo.domain.User;
import com.inomind.modelo.springmongo.dto.UserDTO;

public class MeControllerTest extends ApplicationTest {

	@Test
	public void testMe() {

		User user = signIn();

		ResponseEntity<UserDTO> response = get(ME)
		        .expectedStatus(OK)
		        .getResponse(UserDTO.class);
		
		assertThat(response.getBody().getId(), equalTo(user.getId()));
	}

	@Test
	public void testMe401() {
		get(ME).expectedStatus(UNAUTHORIZED).getResponse();
	}
}
