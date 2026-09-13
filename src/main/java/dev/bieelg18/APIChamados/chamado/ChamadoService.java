package dev.bieelg18.APIChamados.chamado;

import dev.bieelg18.APIChamados.exception.RecursoNaoEncontradoException;
import dev.bieelg18.APIChamados.exception.StatusIncorretoException;
import dev.bieelg18.APIChamados.usuario.Usuario;
import dev.bieelg18.APIChamados.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final CriarChamadoMapper criarChamadoMapper;
    private final ListarChamadosMapper listarChamadosMapper;
    private final UsuarioRepository usuarioRepository;

    //Método para criar/abrir um novo chamado (todos os usuarios)
    public ListarChamadosDTO criarChamado(CriarChamadoDTO criarDTO, Authentication authentication){

        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário autenticado não encontrado"
                ));
        Chamado chamado = criarChamadoMapper.toEntity(criarDTO);
        chamado.setUsuario(usuario);
        chamado.setDataAbertura(LocalDateTime.now());
        chamado.setStatusChamado(StatusChamado.ABERTO);
        Chamado chamadoCriado = chamadoRepository.save(chamado);
        return listarChamadosMapper.toDTO(chamadoCriado);
    }

    //Método para listar todos os chamados
    public List<ListarChamadosDTO> listarChamados(){
        List<Chamado> chamados = chamadoRepository.findAll();
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados que pertencem a quem chamou a requisição
    public List<ListarChamadosDTO> listarChamadosUsuario(Authentication authentication){
        String email = authentication.getName();
        List<Chamado> chamados = chamadoRepository.findByUsuarioEmail(email);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados em aberto
    public List<ListarChamadosDTO> chamadosEmAberto(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.ABERTO);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados encerrados
    public List<ListarChamadosDTO> chamadosEncerrados(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.FECHADO);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados em atendimento
    public List<ListarChamadosDTO> chamadosEmAtendimento(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.EM_ATENDIMENTO);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para listar todos os chamados improcedentes
    public List<ListarChamadosDTO> chamadosImprocedentes(){
        List<Chamado> chamados = chamadoRepository.findByStatusChamado(StatusChamado.IMPROCEDENTE);
        return chamados.stream()
                .map(listarChamadosMapper::toDTO)
                .toList();
    }


    //Método para alterar chamado de aberto para em atendimento
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


    //Método para fechar um chamado
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


    //Método para fechar um chamado como improcedente
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


    //Método para deletar um chamado por id
    public void deletarChamado(Integer numeroChamado){
        Chamado chamado = chamadoRepository.findById(numeroChamado)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Não foi encontrado nenhum chamado com o número " + numeroChamado
                ));
        chamadoRepository.delete(chamado);
    }

}
