package dev.bieelg18.APIChamados.Usuario;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditarPermissaoMapper {

    EditarPermissaoDTO toDTO(Usuario usuario);

    Usuario toEntity(EditarPermissaoDTO editarPermissaoDTO);

}
