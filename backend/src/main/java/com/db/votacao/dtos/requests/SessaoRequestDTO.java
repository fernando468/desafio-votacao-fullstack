package com.db.votacao.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SessaoRequestDTO(
        @NotNull Long pautaId,
        @NotNull LocalDateTime dataInicio,
        @NotNull Integer tempoEmMinuto) {
}
