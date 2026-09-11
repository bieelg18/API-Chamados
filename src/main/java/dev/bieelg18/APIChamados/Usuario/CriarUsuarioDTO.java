package dev.bieelg18.APIChamados.Usuario;

public record CriarUsuarioDTO(
        Integer id,
        String nome,
        String email,
        String senha,
        Permissao permissao
) {
}
