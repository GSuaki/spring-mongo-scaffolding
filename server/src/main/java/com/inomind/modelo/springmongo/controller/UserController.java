package com.inomind.modelo.springmongo.controller;

import static com.inomind.modelo.springmongo.constantes.ControllerConstants.ID;
import static com.inomind.modelo.springmongo.constantes.ControllerConstants.USUARIO;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inomind.modelo.springmongo.domain.User;
import com.inomind.modelo.springmongo.dto.UserDTO;
import com.inomind.modelo.springmongo.exception.UsuarioException;
import com.inomind.modelo.springmongo.form.UsuarioCreateForm;
import com.inomind.modelo.springmongo.form.UsuarioForm;
import com.inomind.modelo.springmongo.repository.UserRepository;

@RestController
@RequestMapping(USUARIO)
public class UserController {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @RequestMapping(value = ID, method = GET)
    public UserDTO findOne(@PathVariable("id") final String id) throws Exception {

        User usuario = usuarioRepository.findOne(id);

        return new UserDTO(usuario);
    }

    @Transactional
    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO create(@Valid @RequestBody UsuarioCreateForm form) throws Exception {
     
        if (form.getSenha().compareTo(form.getConfirmacaoSenha()) != 0)
            throw UsuarioException.SENHAS_NAO_COMPATIVEIS;

        User usuario = new User(form);
        usuario.setPassword(passwordEncoder.encode(form.getSenha()));

        usuarioRepository.save(usuario);

        return new UserDTO(usuario);
    }

    @RequestMapping(method = GET)
    @Transactional(readOnly = true)
    public List<UserDTO> findAll(@RequestParam(value = "ativo", required = false) Boolean ativo) {

        Query query = new Query();

        if (ativo != null)
            query = query(where("ativo").is(ativo));

        List<User> list = mongoTemplate.find(query, User.class);

        return list.stream()
                .map(UserDTO::new)
                .collect(toList());
    }

    @RequestMapping(value = ID, method = DELETE)
    public UserDTO delete(@PathVariable("id") final String id) throws Exception {

        User usuario = usuarioRepository.findOne(id);

        try {
            usuarioRepository.delete(usuario);
        } catch (Exception e) {
            throw UsuarioException.USUARIO_VINCULADO;
        }

        return new UserDTO(usuario);
    }

    @RequestMapping(value = ID, method = PUT, consumes = APPLICATION_JSON_VALUE)
    public UserDTO update(@PathVariable("id") final String id, @Valid @RequestBody UsuarioForm form) throws Exception {

        User usuario = usuarioRepository.findOne(id);

        usuario.setNome(form.getNome());
        usuario.setLogin(form.getLogin());
        usuario.setTelefone(form.getTelefone());
        usuario.setTipoUsuario(form.getTipoUsuario());

        usuarioRepository.save(usuario);

        return new UserDTO(usuario);
    }

}
