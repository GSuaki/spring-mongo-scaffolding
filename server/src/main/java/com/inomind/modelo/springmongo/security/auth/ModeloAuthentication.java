package com.inomind.modelo.springmongo.security.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.inomind.modelo.springmongo.security.DefaultUser;

public class ModeloAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;
	
	private final DefaultUser user;
	
	private boolean authentication = true;
	
	public ModeloAuthentication(DefaultUser user) {
		super();
		this.user = user;
	}

	@Override
	public String getName() {
		return user.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authentication;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authentication = isAuthenticated;
	}

}
