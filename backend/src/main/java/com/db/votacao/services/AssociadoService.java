package com.db.votacao.services;

import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.AssociadoMapper;
import com.db.votacao.models.Associado;
import com.db.votacao.repositories.AssociadoRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociadoService {
    private final AssociadoRepository associadoRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AssociadoService.class.getName());

    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public AssociadoResponseDTO criar(@Valid AssociadoRequestDTO associadoRequestDTO) throws ConflictException {
        String cpfAssociadoLog = associadoRequestDTO.cpf().substring(0, 2);
        LOGGER.info("Iniciando - criar associado com cpf: {}", cpfAssociadoLog);
        Optional<Associado> associado = associadoRepository.findByCpf(associadoRequestDTO.cpf());
        if (associado.isPresent()) {
            LOGGER.error("Encerrado - criar associado com cpf: {}. Erro ao criar associado", cpfAssociadoLog);
            throw new ConflictException("Erro ao criar associado");
        }
        Associado associadoCriado = associadoRepository.save(AssociadoMapper.toEntity(associadoRequestDTO));
        LOGGER.info("Encerrado - criar associado com cpf: {}", cpfAssociadoLog);
        return AssociadoMapper.toResponseDTO(associadoCriado);
    }

    public Associado buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Iniciando - buscar associado com o id: {}", id);
        Associado associado = associadoRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Encerrado - buscar associado com o id: {}. Associado não encontrado", id);
                    return new NotFoundException("Erro ao buscar usuário");
                });
        LOGGER.info("Encerrado - buscar associado com o id: {}", id);
        return associado;
    }
}
