package com.db.votacao.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "sessao")
public class Sessao extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pauta_id", nullable = false, unique = true)
    private Pauta pauta;

    @Column(name = "dataInicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "dataFim", nullable = false)
    private LocalDateTime dataFim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isAberta() {
        LocalDateTime agora = LocalDateTime.now();
        return !agora.isBefore(dataInicio) && agora.isBefore(dataFim);
    }
}
