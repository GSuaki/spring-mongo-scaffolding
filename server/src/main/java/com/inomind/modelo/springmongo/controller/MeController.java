package com.inomind.modelo.springmongo.controller;

import static com.inomind.modelo.springmongo.constantes.ControllerConstants.ME;
import static com.inomind.modelo.springmongo.security.UserUtils.getUserLogged;
import static com.inomind.modelo.springmongo.security.UserUtils.isUserLogged;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inomind.modelo.springmongo.dto.UserDTO;
import com.inomind.modelo.springmongo.exception.WebException;

@RestController
@RequestMapping(ME)
public class MeController {

	@ResponseBody
	@RequestMapping(method = GET)
	public UserDTO me() {

		if (isUserLogged()) {
			return new UserDTO(getUserLogged());
		}

		throw new WebException(HttpStatus.UNAUTHORIZED, "unauthorized");
	}

}
