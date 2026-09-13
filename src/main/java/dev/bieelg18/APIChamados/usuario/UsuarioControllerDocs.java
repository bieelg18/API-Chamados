package dev.bieelg18.APIChamados.usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UsuarioControllerDocs {

    @Operation(summary = "Cria um novo usuário no banco de dados", description = "Não é necessário autenticação para acessar essa rota")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Usuário não criado")
    })
    ListarUsuarioDTO criarUsuario(CriarUsuarioDTO criarDTO);

    @Operation(summary = "Lista todos os usuários cadastrados no banco de dados", description = "É necessário estar autenticado e ter nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    List<ListarUsuarioDTO> usuarios();

    @Operation(summary = "Busca um usuário específico no banco de dados através do e-mail", description = "É necessário estar autenticado e ter nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    ListarUsuarioDTO buscarEmail(String email);

    @Operation(summary = "Deleta um usuário através do ID", description = "É necessário estar autenticado e ter nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado e deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado, exclusão não realizada"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    void deletarUsuario(Integer id);

    @Operation(summary = "Altera dados do usuário", description = "Altera os dados do usuário que chama a requisição. Qualquer nível de permissão pode acessar. É necessário estar autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados alterados"),
            @ApiResponse(responseCode = "404", description = "Dados não alterados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ListarUsuarioDTO alterarDadosCadastro(EditarUsuarioDTO editarDTO, Authentication authentication);

    @Operation(summary = "Edita o nível de permissão de um usuário", description = "É necessário estar autenticado e somente usuários com nível SUPORTE podem acessar essa rota")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissão alterada"),
            @ApiResponse(responseCode = "404", description = "Permissão não alterada"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    ListarUsuarioDTO editarPermissao(Integer id, EditarPermissaoDTO permissaoDTO);

}
