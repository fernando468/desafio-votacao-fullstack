package com.db.votacao.controllers;

import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.services.AssociadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ConfigurationException;

@RestController
@RequestMapping
public class AssociadoController {
    private final AssociadoService associadoService;

    public AssociadoController(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    @PostMapping
    public ResponseEntity<AssociadoResponseDTO> criar(
            @Valid @RequestBody AssociadoRequestDTO associadoRequestDTO) throws ConfigurationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.associadoService.criar(associadoRequestDTO));
    }
}
