package dev.bieelg18.APIChamados.chamado;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ListarChamadosMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    ListarChamadosDTO toDTO(Chamado chamado);

    @Mapping(target = "usuario", ignore = true)
    Chamado toEntity(ListarChamadosDTO listarChamadosDTO);

}
