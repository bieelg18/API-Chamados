package dev.bieelg18.APIChamados.Usuario;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListarUsuarioMapper {

    ListarUsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(ListarUsuarioDTO listarUsuarioDTO);

}
