package com.db.votacao.dtos.requests;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssociadoRequestDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void deveSerValidoQuandoCamposForemPreenchidos() {
        AssociadoRequestDTO dto = new AssociadoRequestDTO("88871221052");

        Set<ConstraintViolation<AssociadoRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void deveSerInvalidoQuandoCpfEstiverComTamanhoDeCampoSuperiorAoPermitido() {
        AssociadoRequestDTO dto = new AssociadoRequestDTO("123456789100");
        Set<ConstraintViolation<AssociadoRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        boolean cpfComTamanhoSuperior = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf"));
        assertTrue(cpfComTamanhoSuperior);
    }


    @Test
    public void deveSerInvalidoQuandoCpfEstiverComTamanhoDeCampoInferiorAoMinimo() {
        AssociadoRequestDTO dto = new AssociadoRequestDTO("1234567891");
        Set<ConstraintViolation<AssociadoRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        boolean cpfComTamanhoSuperior = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf"));
        assertTrue(cpfComTamanhoSuperior);
    }
}
