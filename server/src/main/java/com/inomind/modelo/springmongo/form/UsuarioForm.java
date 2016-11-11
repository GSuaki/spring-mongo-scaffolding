package com.inomind.modelo.springmongo.form;

import javax.validation.constraints.NotNull;

import com.inomind.modelo.springmongo.enums.TipoUsuario;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioForm {

	@NotNull
	private String login;

	@NotNull
	private String nome;
	
	@NotNull
	private TipoUsuario tipoUsuario;
	
	private String telefone;
	
}
