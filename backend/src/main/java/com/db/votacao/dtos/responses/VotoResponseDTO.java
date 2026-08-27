package com.db.votacao.dtos.responses;

import com.db.votacao.enums.TipoVotoEnum;

public record VotoResponseDTO(
        Long id,
        TipoVotoEnum tipoVoto,
        AssociadoResponseDTO associado,
        SessaoResponseDTO sessao) {
}
