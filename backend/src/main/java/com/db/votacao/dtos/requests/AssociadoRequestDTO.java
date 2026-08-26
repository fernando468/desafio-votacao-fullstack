package com.db.votacao.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AssociadoRequestDTO(@NotBlank String cpf) {
}
