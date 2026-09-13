package dev.bieelg18.APIChamados.usuario;

import dev.bieelg18.APIChamados.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CriarUsuarioMapper criarUsuarioMapper;
    private final EditarUsuarioMapper editarUsuarioMapper;
    private final EditarPermissaoMapper editarPermissaoMapper;
    private final ListarUsuarioMapper listarUsuarioMapper;
    private final PasswordEncoder passwordEncoder;

    //Criar/Cadastrar usuários
    public ListarUsuarioDTO criarUsuario(CriarUsuarioDTO criarUsuarioDTO){
        Usuario usuario = criarUsuarioMapper.toEntity(criarUsuarioDTO);
        usuario.setSenha(passwordEncoder.encode(criarUsuarioDTO.senha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return listarUsuarioMapper.toDTO(usuarioSalvo);
    }

    //Listar todos os usuários
    public List<ListarUsuarioDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(listarUsuarioMapper::toDTO)
                .toList();
    }

    //Método para buscar usuários por e-mail
    public ListarUsuarioDTO buscarPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new RecursoNaoEncontradoException(
                        "Usuário com e-mail " + email + " não encontrado"
                ));
        return listarUsuarioMapper.toDTO(usuario);
    }

    //Método para deletar usuário por id
    public void deletarUsuario(Integer id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException(
                        "Usuário com ID " + id + " não encontrado"
                ));
        usuarioRepository.delete(usuario);
    }

    //Método para atualizar dados de cadastro do usuário que chamar a requisição
    public ListarUsuarioDTO editarDadosCadastro(EditarUsuarioDTO editarDTO, Authentication authentication){

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new RecursoNaoEncontradoException(
                                "Usuário autenticado não encontrado"
                        ));

        if (editarDTO.nome() != null){
            usuario.setNome(editarDTO.nome());
        }
        if (editarDTO.email() != null) {
            usuario.setEmail(editarDTO.email());
        }
        if (editarDTO.senha() != null){
            usuario.setSenha(passwordEncoder.encode(editarDTO.senha()));
        }
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return listarUsuarioMapper.toDTO(usuarioSalvo);
    }

    //Método para alterar a permissão de um usuário
    public ListarUsuarioDTO editarPermissao(Integer id, EditarPermissaoDTO permissaoDTO){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário com o ID " + id + " não encontrado"
                ));
        usuario.setPermissao(permissaoDTO.permissao());
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return listarUsuarioMapper.toDTO(usuarioSalvo);
    }

}
