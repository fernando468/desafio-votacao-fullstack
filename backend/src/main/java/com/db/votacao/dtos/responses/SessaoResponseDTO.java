package com.db.votacao.dtos.responses;

import java.time.LocalDateTime;

public record SessaoResponseDTO(
        PautaResponseDTO pauta,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        Boolean isAberta) {
}
