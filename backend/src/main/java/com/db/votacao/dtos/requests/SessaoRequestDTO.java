package com.db.votacao.dtos.requests;

import java.time.LocalDateTime;

public record SessaoRequestDTO(
        Long pautaId,
        LocalDateTime dataInicio,
        LocalDateTime dataFim) {
}
