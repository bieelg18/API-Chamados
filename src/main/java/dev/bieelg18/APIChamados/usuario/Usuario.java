package dev.bieelg18.APIChamados.usuario;

import dev.bieelg18.APIChamados.chamado.Chamado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Permissao permissao;

    @OneToMany(mappedBy = "usuario")
    private List<Chamado> chamados;

    //Método que vai informar ao Spring quais permissões o usuario possui
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + permissao.name())
        );
    }

    //Método que vai informar ao Spring qual campo da entity é a senha
    @Override
    public String getPassword(){
        return senha;
    }

    //Método que vai informar ao Spring qual campo da entity é o username usado no login
    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }


}
