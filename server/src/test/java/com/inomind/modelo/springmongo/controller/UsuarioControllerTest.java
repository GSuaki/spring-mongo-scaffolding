package com.inomind.modelo.springmongo.controller;

import static com.inomind.modelo.springmongo.compose.Compose.user;
import static com.inomind.modelo.springmongo.constantes.ControllerConstants.USUARIO;
import static com.inomind.modelo.springmongo.enums.TipoUsuario.BASICO;
import static java.lang.Boolean.FALSE;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.util.Assert.notNull;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.inomind.modelo.springmongo.ApplicationTest;
import com.inomind.modelo.springmongo.RequestBuilder;
import com.inomind.modelo.springmongo.domain.User;
import com.inomind.modelo.springmongo.dto.UserDTO;
import com.inomind.modelo.springmongo.form.UsuarioCreateForm;

public class UsuarioControllerTest extends ApplicationTest {

    @Before
    public void before() {
        signIn();
    }

    @Test
    public void findOne_idGiven_shouldReturnUser() throws Exception {

        User inomind = user("Marcos Inomind").ativo(true).build();
        
        saveAll(inomind);

        RequestBuilder get = get(USUARIO + "/%s", inomind.getId());

        ResponseEntity<UserDTO> response = get.expectedStatus(OK)
                .getResponse(UserDTO.class);

        assertEquals(response.getBody().getNome(), inomind.getNome());
    }

    @Test
    public void create_formGiven_shouldReturnUser() throws Exception {
        
        UsuarioCreateForm form = UsuarioCreateForm.builder()
            .senha("123")
            .confirmacaoSenha("123")
            .login("new@user.com")
            .nome("new").tipoUsuario(BASICO)
            .build();
        
        RequestBuilder post = post(USUARIO).json(form);
        
        ResponseEntity<UserDTO> response = post.expectedStatus(OK)
                .getResponse(UserDTO.class);
        
        notNull(response.getBody().getId());
        assertEquals(form.getNome(), response.getBody().getNome());
        assertEquals(form.getLogin(), response.getBody().getLogin());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void create_formWithDifferentPasswordGiven_shouldReturnException() throws Exception {
        
        UsuarioCreateForm form = UsuarioCreateForm.builder()
                .senha("123")
                .confirmacaoSenha("1223")
                .login("new@user.com")
                .nome("new").tipoUsuario(BASICO)
                .build();
        
        RequestBuilder post = post(USUARIO).json(form);
        
        ResponseEntity<Map> response = post.expectedStatus(PRECONDITION_FAILED)
                .getResponse(Map.class);
        
        assertEquals("senhas.nao.compativeis", response.getBody().get("message"));
    }

    @Test
    public void findAll_shouldReturnUsers() throws Exception {
        
        User inomind = user("Marcos Inomind").ativo(true).build();
        User reds    = user("Reds").ativo(true).build();
        User marcos  = user("Marcos ").ativo(false).build();
        
        saveAll(inomind, reds, marcos);
        
        RequestBuilder get = get(USUARIO);
        
        ResponseEntity<UserDTO[]> response = get.expectedStatus(OK)
                .getResponse(UserDTO[].class);
        
        assertThat(response.getBody().length, equalTo(4));
    }

    @Test
    public void findAll_shouldReturnActiveUsers() throws Exception {
        
        User inomind = user("Marcos Inomind").ativo(true).build();
        User reds    = user("Reds").ativo(true).build();
        User marcos  = user("Marcos ").ativo(false).build();
        
        saveAll(inomind, reds, marcos);
        
        RequestBuilder get = get(USUARIO).queryParam("ativo", true);
        
        ResponseEntity<UserDTO[]> response = get.expectedStatus(OK)
                .getResponse(UserDTO[].class);
        
        assertThat(response.getBody().length, equalTo(3));
    }

    @Test
    public void findAll_shouldReturnInactiveUsers() throws Exception {
        
        User inomind = user("Marcos Inomind").ativo(true).build();
        User reds    = user("Reds").ativo(true).build();
        User marcos  = user("Marcos ").ativo(false).build();
        
        saveAll(inomind, reds, marcos);
        
        RequestBuilder get = get(USUARIO).queryParam("ativo", false);
        
        ResponseEntity<UserDTO[]> response = get.expectedStatus(OK)
                .getResponse(UserDTO[].class);
        
        assertThat(response.getBody().length, equalTo(1));
    }
    
    @Test
    public void delete_idGiven_shouldReturnUser() throws Exception {

        User inomind = user("Marcos Inomind").ativo(true).build();
        
        saveAll(inomind);

        RequestBuilder get = delete(USUARIO + "/%s", inomind.getId());

        ResponseEntity<UserDTO> response = get.expectedStatus(OK)
                .getResponse(UserDTO.class);

        assertEquals(response.getBody().getNome(), inomind.getNome());
        assertEquals(FALSE, exists(inomind.getId(), User.class));
    }
    
    @Test
    public void update_formGiven_shouldReturnUser() throws Exception {
        
        User inomind = user("Marcos Inomind").ativo(true).build();
        
        saveAll(inomind);
        
        UsuarioCreateForm form = UsuarioCreateForm.builder()
            .login("new@user.com")
            .nome("new")
            .telefone("13231231")
            .tipoUsuario(BASICO)
            .build();
        
        RequestBuilder put = put(USUARIO + "/%s", inomind.getId()).json(form);
        
        ResponseEntity<UserDTO> response = put.expectedStatus(OK)
                .getResponse(UserDTO.class);
        
        assertEquals(form.getNome(), response.getBody().getNome());
        assertEquals(form.getLogin(), response.getBody().getLogin());

        inomind = findOne(inomind.getId(), User.class);
        
        assertEquals(form.getNome(), inomind.getNome());
        assertEquals(form.getLogin(), inomind.getLogin());
    }
}
