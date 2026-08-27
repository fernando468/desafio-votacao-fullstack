package com.db.votacao.controllers;

import com.db.votacao.dtos.requests.SessaoRequestDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.services.SessaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {
    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<SessaoResponseDTO> criarSessao(
            @Valid @RequestBody SessaoRequestDTO sessaoResponseDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.criarSessao(sessaoResponseDTO));
    }
}
