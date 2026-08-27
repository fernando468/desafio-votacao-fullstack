package com.db.votacao.services;

import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.AssociadoMapper;
import com.db.votacao.models.Associado;
import com.db.votacao.repositories.AssociadoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import javax.naming.ConfigurationException;
import java.net.URI;
import java.util.Optional;

@Service
public class AssociadoService {
    private final AssociadoRepository associadoRepository;

    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public AssociadoResponseDTO criar(@Valid AssociadoRequestDTO associadoRequestDTO) throws ConfigurationException {
        Optional<Associado> associado = associadoRepository.findByCpf(associadoRequestDTO.cpf());
        if (associado.isPresent()) {
            throw new ConfigurationException();
        }
        Associado associadoCriado = associadoRepository.save(AssociadoMapper.toEntity(associadoRequestDTO));
        return AssociadoMapper.toResponseDTO(associadoCriado);
    }

    public Associado buscarPorId(Long id) throws NotFoundException {
        return associadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }
}
