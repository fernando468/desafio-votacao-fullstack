package com.db.votacao.controllers;

import com.db.votacao.dtos.requests.VotoRequestDTO;
import com.db.votacao.dtos.responses.VotoResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.services.VotoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votos")
public class VotoController {
    private final VotoService votoService;

    public VotoController(VotoService votoService) {
        this.votoService = votoService;
    }

    @PostMapping
    public ResponseEntity<VotoResponseDTO> criarVoto(@Valid @RequestBody VotoRequestDTO votoRequestDTO) throws ConflictException, NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(votoService.criarVoto(votoRequestDTO));
    }
}
