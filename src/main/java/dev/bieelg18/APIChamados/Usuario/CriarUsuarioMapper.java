package dev.bieelg18.APIChamados.Usuario;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CriarUsuarioMapper {

    CriarUsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(CriarUsuarioDTO criarUsuarioDTO);

}
