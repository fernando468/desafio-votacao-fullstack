package com.db.votacao.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PautaRequestDTO(
        @NotBlank @Size(min = 3, max = 30) String titulo,
        @NotBlank @Size(min = 3, max = 150) String descricao) {
}
