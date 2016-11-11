package com.inomind.modelo.springmongo.domain;

import static java.lang.String.format;
import static javax.persistence.EnumType.STRING;

import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;

import com.inomind.modelo.springmongo.enums.TipoUsuario;
import com.inomind.modelo.springmongo.form.UsuarioCreateForm;
import com.inomind.modelo.springmongo.security.UserCredencial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends AbstractEntity implements UserCredencial {

    private String login;

    private String nome;

    private String password;

    @Enumerated(STRING)
    private TipoUsuario tipoUsuario;

    private String telefone;

    private Boolean ativo;

    @Override
    public String toString() {
        return format("User [id=%s, nome='%s', tipoUsuario='%s']", this.getId(), nome, tipoUsuario);
    }

    public User(UsuarioCreateForm form) {
        
        this.ativo = true;
        this.nome = form.getNome();
        this.login = form.getLogin();
        this.telefone = form.getTelefone();
        this.tipoUsuario = form.getTipoUsuario();
    }
}
