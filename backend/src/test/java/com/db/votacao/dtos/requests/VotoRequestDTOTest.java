package com.db.votacao.dtos.requests;

import com.db.votacao.enums.TipoVotoEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VotoRequestDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void deveSerValidoQuandoCamposForemPreenchidos() {

        VotoRequestDTO dto = new VotoRequestDTO(1L, 1L, TipoVotoEnum.SIM);

        Set<ConstraintViolation<VotoRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void deveSerInvalidoQuandoCamposEstiveremNulo() {

        VotoRequestDTO dto = new VotoRequestDTO(null, null, null);

        Set<ConstraintViolation<VotoRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        boolean associadoIdNulo = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("associadoId"));
        boolean sessaoIdNulo = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("sessaoId"));
        boolean tipoVotoNulo = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("tipoVoto"));

        assertTrue(associadoIdNulo);
        assertTrue(sessaoIdNulo);
        assertTrue(tipoVotoNulo);
    }
}
