package com.db.votacao.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record PautaRequestDTO(
        @NotBlank String titulo,
        @NotBlank String descricao) {
}
