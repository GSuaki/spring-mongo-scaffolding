package com.inomind.modelo.springmongo.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inomind.modelo.springmongo.enums.TipoUsuario;
import com.inomind.modelo.springmongo.jwt.JWTAuthority;
import com.inomind.modelo.springmongo.jwt.JWTPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUser implements UserDetails {

	private static final long serialVersionUID = 9145350109154466481L;

	private String id;
	
	private String nome;
	
	private Boolean ativo;
	
	@JsonIgnore
	private String password;
	
	private String username;
	
	private TipoUsuario tipoUsuario;
	
	@JsonIgnore
	private List<GrantedAuthority> authorities;
	
	private boolean accountNonExpired;
	
	private boolean accountNonLocked;
	
	private boolean credentialsNonExpired;
	
	private boolean enabled;
	
	public static DefaultUser build(JWTPayload payload) {
		return DefaultUser.builder()
			.id(payload.getId())
			.nome(payload.getNome())
			.ativo(payload.getAtivo())
			.username(payload.getUsername())
			.tipoUsuario(payload.getTipoUsuario())
			.authorities(
				payload.getAuthorities()
				.stream()
				.map(auth -> new JWTAuthority(auth))
				.collect(Collectors.toList()))
			.accountNonExpired(payload.isAccountNonExpired())
			.accountNonLocked(payload.isAccountNonLocked())
			.credentialsNonExpired(payload.isCredentialsNonExpired())
			.enabled(payload.isEnabled()).build();
	}
}
