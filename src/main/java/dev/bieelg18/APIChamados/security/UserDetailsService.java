package dev.bieelg18.APIChamados.security;


import dev.bieelg18.APIChamados.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException {
            return usuarioRepository.findByEmail(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Usuário não encontrado"));
        }


}
