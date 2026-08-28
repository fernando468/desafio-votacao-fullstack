package com.db.votacao.dtos.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SessaoResponseDTO(
        Long id,
        PautaResponseDTO pauta,
        LocalDate dataInicio,
        LocalDate dataFim,
        Boolean isAberta) {
}
