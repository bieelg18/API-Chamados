package dev.bieelg18.APIChamados.chamado;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;


    //Rota para criar um chamado (Todos podem acessar, só precisa estar autenticado)
    @PostMapping
    public ListarChamadosDTO criarChamado(@RequestBody CriarChamadoDTO criarDTO, Authentication authentication){
        return chamadoService.criarChamado(criarDTO, authentication);
    }

    //Rota para listar todos os chamados (Precisa estar autenticado e ser nivel suporte)
    @GetMapping
    public List<ListarChamadosDTO> listarChamados(){
        return chamadoService.listarChamados();
    }

    //Rota para acessar os chamados de quem chamou a requisição (Todos podem acessar estando autenticado)
    @GetMapping("/meusChamados")
    public List<ListarChamadosDTO> chamadosUsuario(Authentication authentication){
        return chamadoService.listarChamadosUsuario(authentication);
    }

    //Rota para listar chamados em aberto (Precisa estar autenticado e ser nivel suporte)
    @GetMapping("/aberto")
    public List<ListarChamadosDTO> abertos(){
        return chamadoService.chamadosEmAberto();
    }

    //Rota para listar chamados fechados (Precisa estar autenticado e ser nivel suporte)
    @GetMapping("/fechado")
    public List<ListarChamadosDTO> fechados(){
        return chamadoService.chamadosEncerrados();
    }

    //Rota para listar chamados em atendimento (Precisa estar autenticado e ser nivel suporte)
    @GetMapping("/atendimento")
    public List<ListarChamadosDTO> emAtendimento(){
        return chamadoService.chamadosEmAtendimento();
    }

    //Rota para listar chamados improcedentes (Precisa estar autenticado e ser nivel suporte)
    @GetMapping("/improcedente")
    public List<ListarChamadosDTO> improcedentes(){
        return chamadoService.chamadosImprocedentes();
    }

    //Rota para alterar um chamado de aberto para em atendimento (Precisa estar autenticado e ser nivel suporte)
    @PatchMapping("/atendimento/{id}")
    public ListarChamadosDTO atendimento(@PathVariable Integer id){
        return chamadoService.emAtendimento(id);
    }

    //Rota para fechar um chamado (Precisa estar autenticado e ser nivel suporte)
    @PatchMapping("/fechar/{id}")
    public ListarChamadosDTO fecharChamado(@PathVariable Integer id){
        return chamadoService.fecharChamado(id);
    }

    //Rota para alterar chamado para improcedente (Precisa estar autenticado e ser nivel suporte)
    @PatchMapping("/improcedente/{id}")
    public ListarChamadosDTO improcedente(@PathVariable Integer id){
        return chamadoService.chamadoImprocedente(id);
    }

    //Rota para deletar um chamado (Precisa estar autenticado e ser nivel suporte)
    @DeleteMapping("/{id}")
    public void deletarChamado(@PathVariable Integer id){
        chamadoService.deletarChamado(id);
    }

}
