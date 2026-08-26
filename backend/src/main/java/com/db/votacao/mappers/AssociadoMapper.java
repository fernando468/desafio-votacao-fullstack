package com.db.votacao.mappers;

import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.models.Associado;

public class AssociadoMapper {
    public static AssociadoResponseDTO toResponseDTO(Associado associado) {
        return new AssociadoResponseDTO(associado.getId(), associado.getCpf());
    }

    public static Associado toEntity(AssociadoRequestDTO associadoRequestDTO) {
        Associado associado = new Associado();
        associado.setCpf(associado.getCpf());
        return associado;
    }
}
