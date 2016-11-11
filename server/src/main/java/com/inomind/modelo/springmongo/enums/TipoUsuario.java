package com.inomind.modelo.springmongo.enums;

import com.inomind.modelo.springmongo.security.UserRoles;

public enum TipoUsuario {

	BASICO(UserRoles.ROLE_USER), 
	ADMINISTRADOR(UserRoles.ROLE_ADMIN), 
	MASTER(UserRoles.ROLE_MASTER),
	INOMIND(UserRoles.ROLE_INOMIND);
	
	private String role;
	
	private TipoUsuario(String role){
		this.role = role;
	}
	
	public String getRole(){
		return this.role;
	}
	
}
