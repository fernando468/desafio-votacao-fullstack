package com.db.votacao.controllers;

import com.db.votacao.configs.InternalServerErrorResponse;
import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.dtos.responses.ErroResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.services.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ConfigurationException;

@RestController
@RequestMapping("/associados")
@Tag(name = "Associados", description = "Endpoints dos associados")
public class AssociadoController {
    private final AssociadoService associadoService;

    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo cooperado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associado criado com sucesso"),
            @ApiResponse(
                    responseCode = "409",
                    description = "Erro ao criar o associado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))
            )
    })
    @InternalServerErrorResponse
    public ResponseEntity<AssociadoResponseDTO> criar(
            @Valid @RequestBody AssociadoRequestDTO associadoRequestDTO) throws ConflictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.associadoService.criar(associadoRequestDTO));
    }
}
