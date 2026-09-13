package dev.bieelg18.APIChamados.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerDocs{

    private final UsuarioService usuarioService;


    //Rota para se cadastrar (Todos podem acessar e não precisa estar autenticado)
    @Override
    @PostMapping
    public ListarUsuarioDTO criarUsuario(@RequestBody CriarUsuarioDTO criarDTO){
        return usuarioService.criarUsuario(criarDTO);
    }

    //Rota para listar todos os usuário cadastrados (Precisa estar autenticado e ser nivel suporte)
    @Override
    @GetMapping
    public List<ListarUsuarioDTO> usuarios(){
        return usuarioService.listarUsuarios();
    }

    //Rota para buscar usuario por e-mail (Precisa estar autenticado e ser nivel suporte)
    @Override
    @GetMapping("/buscar")
    public ListarUsuarioDTO buscarEmail(@RequestParam String email){
        return usuarioService.buscarPorEmail(email);
    }

    //Rota para deletar usuarios por id (Precisa estar autenticado e ser nivel suporte)
    @Override
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Integer id){
        usuarioService.deletarUsuario(id);
    }

    //Rota para atualizar dados de cadastro (Todos podem acessar, só precisa estar autenticado, da para alterar somente o proprio cadastro de quem chamou a requisição, não importe o nivel)
    @Override
    @PatchMapping("/me")
    public ListarUsuarioDTO alterarDadosCadastro(@RequestBody EditarUsuarioDTO editarDTO, Authentication authentication){
        return usuarioService.editarDadosCadastro(editarDTO, authentication);
    }

    //Rota para alterar a permissao de um usuario ja cadastrado (Precisa estar autenticado e ser nivel suporte)
    @Override
    @PatchMapping("/permissao/{id}")
    public ListarUsuarioDTO editarPermissao(@PathVariable Integer id, @RequestBody EditarPermissaoDTO permissaoDTO){
        return usuarioService.editarPermissao(id, permissaoDTO);
    }

}
