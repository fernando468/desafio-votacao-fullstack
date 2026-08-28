package com.db.votacao.controllers;

import com.db.votacao.configs.InternalServerErrorResponse;
import com.db.votacao.dtos.requests.PautaRequestDTO;
import com.db.votacao.dtos.responses.PautaResponseDTO;
import com.db.votacao.services.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pautas")
@Tag(name = "Pautas", description = "Endpoints das pautas")
public class PautaController {
    private final PautaService pautaService;

    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova pauta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pauta criada com sucesso")
    })
    @InternalServerErrorResponse
    public ResponseEntity<PautaResponseDTO> criarPauta(
            @Valid @RequestBody PautaRequestDTO pautaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaService.criarPauta(pautaRequestDTO));
    }

    @GetMapping("/paginacao")
    @Operation(summary = "Busca as pautas com paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listagem das pautas")
    })
    public ResponseEntity<?> buscarPaginado(
            @RequestParam(name = "pagina") Integer pagina,
            @RequestParam(name = "tamanho") Integer tamanho
    ) {
        return ResponseEntity.ok(pautaService.buscarPautas(pagina, tamanho));
    }
}
