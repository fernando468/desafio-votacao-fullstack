package com.db.votacao.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssociadoRequestDTO(@NotBlank @Size(min = 11, max = 11) String cpf) {
}
