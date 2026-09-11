package dev.bieelg18.APIChamados.Chamado;

import dev.bieelg18.APIChamados.Usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numeroChamado;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private StatusChamado statusChamado;

    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

}
