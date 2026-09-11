package dev.bieelg18.APIChamados.Chamado;

import dev.bieelg18.APIChamados.Exception.RecursoNaoEncontradoException;
import dev.bieelg18.APIChamados.Exception.StatusIncorretoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final CriarChamadoMapper criarChamadoMapper;
    private final ListarChamadosMapper listarChamadosMapper;

    //Método para criar/abrir um novo chamado (todos os usuarios)
    public ListarChamadosDTO criarChamado(CriarChamadoDTO criarDTO){
        Chamado chamado = criarChamadoMapper.toEntity(criarDTO);
        //Colocar aqui uma forma de pegar o id do usuario que esta abrindo o chamado
        chamado.setDataAbertura(LocalDateTime.now());
        chamado.setStatusChamado(StatusChamado.ABERTO);
        Chamado chamadoCriado = chamadoRepository.save(chamado);
        return listarChamadosMapper.toDTO(chamadoCriado);
    }

    //Método para listar todos os chamados (somente usuarios de nivel suporte)
    public List<ListarChamadosDTO> listarChamados(){
        List<Chamado> chamados = chamadoRepository.findAll();
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados que pertencem a quem chamou a requisição (todos os usuarios tem acesso)
    public List<ListarChamadosDTO> listarChamadosUsuario(){
        List<Chamado> chamados = chamadoRepository.findAll();
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados em aberto (somente suporte)
    public List<ListarChamadosDTO> chamadosEmAberto(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.ABERTO);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados encerrados (somente suporte)
    public List<ListarChamadosDTO> chamadosEncerrados(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.FECHADO);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados em atendimento (somente suporte)
    public List<ListarChamadosDTO> chamadosEmAtendimento(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.EM_ATENDIMENTO);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados improcedentes (somente suporte)
    public List<ListarChamadosDTO> chamadosImprocedentes(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.IMPROCEDENTE);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para alterar chamado de aberto para em atendimento (somente suporte)
    public ListarChamadosDTO emAtendimento(Integer numeroChamado) {
        Chamado chamado = chamadoRepository.findById(numeroChamado)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Não foi encontrado nenhum chamado com o número " + numeroChamado
                ));
        if (chamado.getStatusChamado() != StatusChamado.ABERTO) {
            throw new StatusIncorretoException(
                    "O chamado precisa estar com status ABERTO para ser colocado EM_ATENDIMENTO"
            );
        }
        chamado.setStatusChamado(StatusChamado.EM_ATENDIMENTO);
        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        return listarChamadosMapper.toDTO(chamadoSalvo);
    }


    //Método para fechar um chamado (somente suporte)
    public ListarChamadosDTO fecharChamado(Integer numeroChamado){
        Chamado chamado = chamadoRepository.findById(numeroChamado)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Não foi encontrado nenhum chamado com o número " + numeroChamado
                ));
        if (chamado.getStatusChamado() != StatusChamado.ABERTO && chamado.getStatusChamado() != StatusChamado.EM_ATENDIMENTO){
            throw new StatusIncorretoException(
                    "O chamado precisa estar com status ABERTO ou EM_ATENDIMENTO para ser fechado"
            );
        }
        chamado.setStatusChamado(StatusChamado.FECHADO);
        chamado.setDataFechamento(LocalDateTime.now());
        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        return listarChamadosMapper.toDTO(chamadoSalvo);
    }


    //Método para fechar um chamado como improcedente (somente suporte)
    public ListarChamadosDTO chamadoImprocedente(Integer numeroChamado){
        Chamado chamado = chamadoRepository.findById(numeroChamado)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Não foi encontrado nenhum chamado com o número " + numeroChamado
                ));
        if (chamado.getStatusChamado() != StatusChamado.ABERTO && chamado.getStatusChamado() != StatusChamado.EM_ATENDIMENTO){
            throw new StatusIncorretoException(
                    "O chamado precisa estar com status ABERTO ou EM_ATENDIMENTO para ser fechado como improcedente"
            );
        }
        chamado.setStatusChamado(StatusChamado.IMPROCEDENTE);
        chamado.setDataFechamento(LocalDateTime.now());
        Chamado chamadoSalvo = chamadoRepository.save(chamado);
        return listarChamadosMapper.toDTO(chamadoSalvo);
    }


    //Método para deletar um chamado por id (somente suporte)
    public void deletarChamado(Integer numeroChamado){
        Chamado chamado = chamadoRepository.findById(numeroChamado)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Não foi encontrado nenhum chamado com o número " + numeroChamado
                ));
        chamadoRepository.delete(chamado);
    }

}
