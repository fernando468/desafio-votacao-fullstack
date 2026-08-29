package com.db.votacao.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record AssociadoRequestDTO(@NotBlank @CPF @Size(min = 11, max = 11) String cpf) {
}
