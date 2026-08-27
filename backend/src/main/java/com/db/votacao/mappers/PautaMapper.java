package com.db.votacao.mappers;

import com.db.votacao.dtos.requests.PautaRequestDTO;
import com.db.votacao.dtos.responses.PautaResponseDTO;
import com.db.votacao.models.Pauta;

public class PautaMapper {
    public static Pauta toEntity(PautaRequestDTO pautaRequestDTO) {
        Pauta pauta = new Pauta();
        pauta.setTitulo(pautaRequestDTO.titulo());
        pauta.setDescricao(pautaRequestDTO.descricao());
        return pauta;
    }

    public static PautaResponseDTO toResponseDTO(Pauta pauta) {
        return new PautaResponseDTO(
                pauta.getTitulo(),
                pauta.getDescricao()
        );
    }
}
