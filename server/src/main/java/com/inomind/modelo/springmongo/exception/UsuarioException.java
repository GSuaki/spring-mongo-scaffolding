package com.inomind.modelo.springmongo.exception;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UsuarioException {

	public static final ModeloException SENHAS_NAO_COMPATIVEIS = new ModeloException("senhas.nao.compativeis", PRECONDITION_FAILED.value());
	public static final ModeloException USUARIO_INVALIDO = new ModeloException("usuario.invalido", PRECONDITION_FAILED.value());
	public static final ModeloException USUARIO_INATIVO = new ModeloException("usuario.inativo", PRECONDITION_FAILED.value());
	public static final ModeloException ACAO_INVALIDA = new ModeloException("acao.invalida", PRECONDITION_FAILED.value());
	public static final ModeloException TOKEN_INVALIDO = new ModeloException("token.invalido", PRECONDITION_FAILED.value());
	public static final ModeloException TOKEN_VENCIDO = new ModeloException("token.vencido", PRECONDITION_FAILED.value());
	public static final ModeloException USER_UNAUTHORIZED = new ModeloException("unauthorized", UNAUTHORIZED.value());
	public static final ModeloException USUARIO_VINCULADO = new ModeloException("usuario.vinculado", PRECONDITION_FAILED.value());
	
}
