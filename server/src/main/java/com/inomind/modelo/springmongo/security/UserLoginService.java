package com.inomind.modelo.springmongo.security;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.inomind.modelo.springmongo.domain.User;
import com.inomind.modelo.springmongo.exception.UsuarioException;
import com.inomind.modelo.springmongo.repository.UserRepository;

@Component
public class UserLoginService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User u = userRepository.findByLogin(username);
		
		if (u == null) {
			throw UsuarioException.USUARIO_INVALIDO;
		} 

		return buildPrincipal(u);
	}

	public DefaultUser buildPrincipal(User u) {

		DefaultUser user = new DefaultUser();
		user.setId(u.getId());
		user.setNome(u.getNome());
		user.setUsername(u.getLogin());
		user.setAtivo(u.getAtivo());
		user.setPassword(u.getPassword());
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setTipoUsuario(u.getTipoUsuario());

		user.setAuthorities(createAuthorityList(u.getTipoUsuario().getRole()));

		return user;
	}

}
