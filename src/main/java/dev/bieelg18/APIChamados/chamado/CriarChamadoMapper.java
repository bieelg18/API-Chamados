package dev.bieelg18.APIChamados.chamado;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CriarChamadoMapper {

    CriarChamadoDTO toDTO(Chamado chamado);

    Chamado toEntity(CriarChamadoDTO criarChamadoDTO);

}
