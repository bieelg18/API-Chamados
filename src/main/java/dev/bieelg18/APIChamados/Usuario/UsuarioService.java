package dev.bieelg18.APIChamados.Usuario;

import dev.bieelg18.APIChamados.Exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
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

    //Criar/Cadastrar usuários
    public ListarUsuarioDTO criarUsuario(CriarUsuarioDTO criarUsuarioDTO){
        Usuario usuario = criarUsuarioMapper.toEntity(criarUsuarioDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return listarUsuarioMapper.toDTO(usuarioSalvo);
    }

    //Listar todos os usuários (Implementar para que somente usuários com nivel Suporte possam acessar o método)
    public List<ListarUsuarioDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(listarUsuarioMapper::toDTO)
                .toList();
    }

    //Método para buscar usuários por e-mail (Implementar para que somente usuários com nivel Suporte possam acessar o método)
    public ListarUsuarioDTO buscarPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new RecursoNaoEncontradoException(
                        "Usuário com e-mail " + email + " não encontrado"
                ));
        return listarUsuarioMapper.toDTO(usuario);
    }

    //Método para deletar usuário por id (Implementar para que somente usuários com nivel Suporte possam acessar o método
    public void deletarUsuario(Integer id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException(
                        "Usuário com ID " + id + " não encontrado"
                ));
        usuarioRepository.delete(usuario);
    }

    //Método para atualizar dados de cadastro do usuário que chamar a requisição (Todos podem acessar)
    public ListarUsuarioDTO editarDadosCadastro(Integer id, EditarUsuarioDTO editarDTO){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário com ID " + id + " não encontrado"
                ));
        usuario.setNome(editarDTO.nome());
        usuario.setEmail(editarDTO.email());
        usuario.setSenha(editarDTO.senha());
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return listarUsuarioMapper.toDTO(usuarioSalvo);
    }

    //Método para alterar a permissão de um usuário (Somente usuários com nivel Suporte podem acessar)
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
