package com.db.votacao.controllers;

import com.db.votacao.configs.InternalServerErrorResponse;
import com.db.votacao.dtos.requests.SessaoRequestDTO;
import com.db.votacao.dtos.responses.ErroResponseDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.exceptions.BadRequestException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.services.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
@Tag(name = "Sessões", description = "Endpoints das sessões")
public class SessaoController {
    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova sessão")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão criada com sucesso"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pauta não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponseDTO.class))
            )
    })
    @InternalServerErrorResponse
    public ResponseEntity<SessaoResponseDTO> criarSessao(
            @Valid @RequestBody SessaoRequestDTO sessaoResponseDTO) throws NotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.criarSessao(sessaoResponseDTO));
    }

    @GetMapping("/paginacao")
    @Operation(summary = "Busca a listagem de sessões com paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listagem das sessões")
    })
    public ResponseEntity<Page<SessaoResponseDTO>> buscarPaginado(
            @RequestParam(name = "pagina") Integer pagina,
            @RequestParam(name = "tamanho") Integer tamanho
    ) {
        return ResponseEntity.ok(sessaoService.buscarSessaoPaginado(pagina, tamanho));
    }
}
