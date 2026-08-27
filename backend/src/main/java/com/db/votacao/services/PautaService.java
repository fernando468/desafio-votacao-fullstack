package com.db.votacao.services;

import com.db.votacao.dtos.requests.PautaRequestDTO;
import com.db.votacao.dtos.responses.PautaResponseDTO;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.PautaMapper;
import com.db.votacao.models.Pauta;
import com.db.votacao.repositories.PautaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PautaService {
    private final PautaRepository pautaRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class.getName());

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public PautaResponseDTO criarPauta(PautaRequestDTO pautaRequestDTO) {
        LOGGER.info("Iniciando - criar pauta");

        Pauta pautaCriada = pautaRepository.save(PautaMapper.toEntity(pautaRequestDTO));

        LOGGER.info("Encerrado - criar pauta");
        return PautaMapper.toResponseDTO(pautaCriada);
    }

    public Pauta buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Iniciando - buscar pauta com o id: {}", id);

        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.info("Encerrado - buscar pauta com o id: {}. Erro ao buscar pauta", id);
                    return new NotFoundException("Pauta não econtrada");
                });

        LOGGER.info("Encerrado - buscar pauta com o id: {}", id);

        return pauta;
    }
}
