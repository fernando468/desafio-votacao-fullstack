package com.db.votacao.models;

import com.db.votacao.enums.TipoVotoEnum;
import jakarta.persistence.*;

@Entity(name = "voto")
public class Voto extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipoVoto")
    @Enumerated(EnumType.STRING)
    private TipoVotoEnum tipoVoto;

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
}
