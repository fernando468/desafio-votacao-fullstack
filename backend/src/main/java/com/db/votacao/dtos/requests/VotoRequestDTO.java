package com.db.votacao.dtos.requests;

import com.db.votacao.enums.TipoVotoEnum;
import jakarta.validation.constraints.NotNull;

public record VotoRequestDTO(
        @NotNull Long associadoId,
        @NotNull Long sessaoId,
        @NotNull TipoVotoEnum tipoVoto) {
}
