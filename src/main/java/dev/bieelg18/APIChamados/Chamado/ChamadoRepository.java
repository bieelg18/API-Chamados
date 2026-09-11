package dev.bieelg18.APIChamados.Chamado;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

    List<Chamado> findByStatusChamado(StatusChamado statusChamado);

}
