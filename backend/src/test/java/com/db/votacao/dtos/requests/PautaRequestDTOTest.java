package com.db.votacao.dtos.requests;

import com.db.votacao.utils.StringUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PautaRequestDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void deveSerValidoQuandoCamposForemPreenchidos() {
        PautaRequestDTO dto = new PautaRequestDTO("Título","Descrição");

        Set<ConstraintViolation<PautaRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void deveSerInvalidoQuandoTituloEDescricaoEstiverEmBranco() {
        PautaRequestDTO dto = new PautaRequestDTO("","");

        Set<ConstraintViolation<PautaRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        boolean tituloNaoExiste = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo"));
        boolean descricaoNaoExiste = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descricao"));
        assertTrue(tituloNaoExiste);
        assertTrue(descricaoNaoExiste);
    }

    @Test
    public void deveSerInvalidoQuandoTituloEDescricaoEstiveremComTamanhoDeCampoSuperiorAoPermitido() {
        PautaRequestDTO dto = new PautaRequestDTO(
                StringUtils.gerarString(100),
                StringUtils.gerarString(200)
        );

        Set<ConstraintViolation<PautaRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        boolean descricaoComCaracteresAcimaDoLimite = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descricao"));
        boolean tituloComCaracteresAcimaDoLimite = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo"));

        assertTrue(tituloComCaracteresAcimaDoLimite);
        assertTrue(descricaoComCaracteresAcimaDoLimite);
    }

    @Test
    public void deveSerInvalidoQuandoTituloEDescricaoEstiveremComTamanhoDeCampoInferiorAoMinimo() {
        PautaRequestDTO dto = new PautaRequestDTO(
                StringUtils.gerarString(2),
                StringUtils.gerarString(2)
        );

        Set<ConstraintViolation<PautaRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        boolean descricaoComCaracteresAcimaDoLimite = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("descricao"));
        boolean tituloComCaracteresAcimaDoLimite = violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo"));

        assertTrue(tituloComCaracteresAcimaDoLimite);
        assertTrue(descricaoComCaracteresAcimaDoLimite);
    }

}
