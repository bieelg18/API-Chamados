package dev.bieelg18.APIChamados.usuario;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditarUsuarioMapper {

    EditarUsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(EditarUsuarioDTO editarUsuarioDTO);

}
