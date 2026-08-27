package com.db.votacao.dtos.requests;

import com.db.votacao.enums.TipoVotoEnum;

public record VotoRequestDTO(
        Long associadoId,
        Long sessaoId,
        TipoVotoEnum tipoVoto) {
}
