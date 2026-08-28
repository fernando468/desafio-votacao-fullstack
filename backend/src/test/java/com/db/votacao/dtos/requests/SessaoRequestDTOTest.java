package com.db.votacao.dtos.requests;

import com.db.votacao.utils.StringUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SessaoRequestDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void deveSerValidoQuandoCamposForemPreenchidos() {

        SessaoRequestDTO dto = new SessaoRequestDTO(1L, LocalDate.now(), LocalDate.now());


        Set<ConstraintViolation<SessaoRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void deveSerInvalidoQuandoCamposEstiveremNulo() {

        SessaoRequestDTO dto = new SessaoRequestDTO(null, null, null);

        Set<ConstraintViolation<SessaoRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        boolean pautaIdNula = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("pautaId"));
        boolean dataInicioNula = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dataInicio"));
        boolean dataFimNula = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dataFim"));

        assertTrue(pautaIdNula);
        assertTrue(dataInicioNula);
        assertTrue(dataFimNula);
    }

}
