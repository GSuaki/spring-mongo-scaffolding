package com.inomind.modelo.springmongo.form;

import javax.validation.constraints.NotNull;

import com.inomind.modelo.springmongo.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCreateForm {

	@NotNull
	private String login;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String senha;
	
	@NotNull
	private String confirmacaoSenha;
	
	@NotNull
	private TipoUsuario tipoUsuario;
	
	private String telefone;
	
}
