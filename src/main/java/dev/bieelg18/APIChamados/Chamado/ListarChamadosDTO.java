package dev.bieelg18.APIChamados.Chamado;



import java.time.LocalDateTime;

public record ListarChamadosDTO(
        Integer numeroChamado,
        String descricao,
        StatusChamado statusChamado,
        LocalDateTime dataAbertura,
        LocalDateTime dataFechamento,
        Integer usuarioId
) {
}
