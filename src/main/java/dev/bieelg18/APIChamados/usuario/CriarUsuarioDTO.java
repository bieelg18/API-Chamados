package dev.bieelg18.APIChamados.usuario;

public record CriarUsuarioDTO(
        Integer id,
        String nome,
        String email,
        String senha,
        Permissao permissao
) {
}
