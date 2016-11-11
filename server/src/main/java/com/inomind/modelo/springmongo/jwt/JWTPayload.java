/**
 * 
 */
package com.inomind.modelo.springmongo.jwt;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inomind.modelo.springmongo.enums.TipoUsuario;
import com.inomind.modelo.springmongo.security.DefaultUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author GSuaki
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JWTPayload {
	
	private String id;
	
	private String nome;
	
	private Boolean ativo;
	
	private String username;
	
	@JsonIgnore
	private String password;
	
	private TipoUsuario tipoUsuario;
	
	private List<JWTAuthority> authorities;
	
	private boolean accountNonExpired;
	
	private boolean accountNonLocked;
	
	private boolean credentialsNonExpired;
	
	private boolean enabled;
	
	public static JWTPayload build(DefaultUser user) {
		return JWTPayload.builder()
			.id(user.getId())
			.nome(user.getNome())
			.ativo(user.getAtivo())
			.username(user.getUsername())
			.tipoUsuario(user.getTipoUsuario())
			.authorities(
				user.getAuthorities()
				.stream()
				.map(auth -> new JWTAuthority(auth))
				.collect(Collectors.toList()))
			.accountNonExpired(user.isAccountNonExpired())
			.accountNonLocked(user.isAccountNonLocked())
			.credentialsNonExpired(user.isCredentialsNonExpired())
			.enabled(user.isEnabled()).build();
	}
}
