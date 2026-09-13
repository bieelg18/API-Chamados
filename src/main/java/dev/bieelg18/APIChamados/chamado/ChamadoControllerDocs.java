package dev.bieelg18.APIChamados.chamado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ChamadoControllerDocs {

    @Operation(summary = "Cria um novo chamado no banco de dados", description = "É necessário estar autenticado para criar um novo chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chamado criado"),
            @ApiResponse(responseCode = "400", description = "Chamado não criado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ListarChamadosDTO criarChamado(CriarChamadoDTO criarDTO, Authentication authentication);

    @Operation(summary = "Lista todos os chamados criados", description = "É necessário estar autenticado e ser nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    List<ListarChamadosDTO> listarChamados();

    @Operation(summary = "Lista os chamados que pertencem ao usuário que chamou a requisição", description = "É necessário estar autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados do usuário"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    List<ListarChamadosDTO> chamadosUsuario(Authentication authentication);

    @Operation(summary = "Lista todos os chamados com status ABERTO", description = "É necessário estar autenticado e ser nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista dos chamados em aberto"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    List<ListarChamadosDTO> abertos();

    @Operation(summary = "Lista todos os chamados com status FECHADO", description = "É necessário estar autenticado e ser nível suporte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados fechados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    List<ListarChamadosDTO> fechados();

    @Operation(summary = "Lista todos os chamados com status EM_ATENDIMENTO", description = "É necessário estar autenticado e ser nível suporte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados em atendimento"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    List<ListarChamadosDTO> emAtendimento();

    @Operation(summary = "Lista todos os chamados com status IMPROCEDENTE", description = "É necessário estar autenticado e ser nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados improcedentes"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    List<ListarChamadosDTO> improcedentes();

    @Operation(summary = "ALtera o status de um chamado em ABERTO para EM_ATENDIMENTO",
    description = "É necessário que o status do chamado esteja em ABERTO. É necessário estar autenticado e ser nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do chamado alterado"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado"),
            @ApiResponse(responseCode = "409", description = "Chamado com status incorreto"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    ListarChamadosDTO atendimento(Integer id);

    @Operation(summary = "Altera o status de um chamado para FECHADO",
    description = "É necessário que o status do chamado esteja em ABERTO ou EM_ATENDIMENTO. É necessário estar autenticado e ser nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do chamado alterado"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado"),
            @ApiResponse(responseCode = "409", description = "Chamado com status incorreto"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    ListarChamadosDTO fecharChamado(Integer id);

    @Operation(summary = "Altera o status de um chamado para IMPROCEDENTE",
    description = "É necessário que o status do chamado esteja em ABERTO ou EM_ATENDIMENTO. É necessário estar autenticado e ser nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do chamado alterado"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado"),
            @ApiResponse(responseCode = "409", description = "Chamado com status incorreto"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    ListarChamadosDTO improcedente(Integer id);

    @Operation(summary = "Deleta um chamado através do ID", description = "É necessário estar autenticado e ser nível SUPORTE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chamado deletado"),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado, exclusão não realizada"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar este recurso")
    })
    void deletarChamado(Integer id);

}
