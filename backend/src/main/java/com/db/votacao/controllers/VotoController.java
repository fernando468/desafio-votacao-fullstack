package com.db.votacao.controllers;

import com.db.votacao.configs.InternalServerErrorResponse;
import com.db.votacao.dtos.requests.VotoRequestDTO;
import com.db.votacao.dtos.responses.ErroResponseDTO;
import com.db.votacao.dtos.responses.VotoResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.services.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

@RestController
@RequestMapping("/votos")
@Tag(name = "Votos", description = "Endpoints dos votos")
public class VotoController {
    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    @Operation(summary = "Cria um voto novo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voto criado com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não foi encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponseDTO.class),
                            examples = { @ExampleObject(name = "Sessão não encontrada"), @ExampleObject(name = "Associado não encontrado") }
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Sessão não esta aberta",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ocorreu um conflito no recurso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErroResponseDTO.class),
                            examples = { @ExampleObject(name = "Sessão não esta aberta"), @ExampleObject(name = "Associado já votou") }
                    )
            ),
    })
    @InternalServerErrorResponse
    public ResponseEntity<VotoResponseDTO> criarVoto(@Valid @RequestBody VotoRequestDTO votoRequestDTO) throws ConflictException, NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(votoService.criarVoto(votoRequestDTO));
    }
}
