package com.inomind.modelo.springmongo.compose;

import static com.inomind.modelo.springmongo.enums.TipoUsuario.INOMIND;

import java.util.concurrent.atomic.AtomicLong;

import com.inomind.modelo.springmongo.domain.User;

public class Compose {

	@SuppressWarnings("unused")
	private static AtomicLong counter = new AtomicLong();

	public static User.UserBuilder user(String name) {
	    return User.builder().nome(name).ativo(true).login(name).password(name).tipoUsuario(INOMIND);
	}
}
