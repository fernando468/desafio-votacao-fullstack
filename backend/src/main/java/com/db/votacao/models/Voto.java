package com.db.votacao.models;

import com.db.votacao.enums.TipoVotoEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "voto")
@Table(
        name = "votos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_voto_associado_pauta",
                        columnNames = {"associado_id", "pauta_id"}
                )
        }
)
public class Voto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipoVoto", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private TipoVotoEnum tipoVoto;

    @Column(nullable = false)
    private LocalDate dataVotacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "associado_id")
    private Associado associado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoVotoEnum getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(TipoVotoEnum tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public LocalDate getDataVotacao() {
        return dataVotacao;
    }

    public void setDataVotacao(LocalDate dataVotacao) {
        this.dataVotacao = dataVotacao;
    }

    public Associado getAssociado() {
        return associado;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }
}
