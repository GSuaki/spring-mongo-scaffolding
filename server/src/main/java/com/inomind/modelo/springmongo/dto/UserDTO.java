package com.inomind.modelo.springmongo.dto;

import com.inomind.modelo.springmongo.domain.User;
import com.inomind.modelo.springmongo.enums.TipoUsuario;
import com.inomind.modelo.springmongo.security.DefaultUser;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

	private String id;

	private String nome;

	private String login;
	
	private TipoUsuario tipoUsuario;
	
	private String telefone;
	
	private Boolean ativo;
	
	private Boolean controleFiscal;
	
	private Boolean controlaNfe;
	
	public UserDTO(DefaultUser user) {
		this.id = user.getId();
		this.nome = user.getNome();
		this.tipoUsuario = user.getTipoUsuario();
	}
	
	public UserDTO(User usuario){
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.login = usuario.getLogin();
		this.tipoUsuario = usuario.getTipoUsuario();
		this.telefone = usuario.getTelefone();
		this.ativo = usuario.getAtivo();
	}

}
