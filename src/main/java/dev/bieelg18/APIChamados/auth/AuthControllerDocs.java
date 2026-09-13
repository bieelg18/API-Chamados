package dev.bieelg18.APIChamados.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface AuthControllerDocs {

    @Operation(summary = "Rota de login para autenticar o usuário e gerar o JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado e JWT gerado"),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha inválidos, JWT não gerado")
    })
    TokenDTO login(LoginDTO loginDTO);

}
