package com.db.votacao.services;

import com.db.votacao.dtos.requests.PautaRequestDTO;
import com.db.votacao.dtos.responses.PautaResponseDTO;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.PautaMapper;
import com.db.votacao.models.Pauta;
import com.db.votacao.repositories.PautaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PautaService extends PaginacaoService {
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

    public Page<PautaResponseDTO> buscarPautaPaginado(int pagina, int tamanho) {
        LOGGER.info("Iniciando - buscar pautas (pagina={}, tamanho={})", pagina, tamanho);
        Page<PautaResponseDTO> pautas = paginar(Pauta.class, pagina, tamanho, "id", Sort.Direction.ASC, PautaMapper::toResponseDTO);
        LOGGER.info("Encerrado - buscar pautas (pagina={}, tamanho={}, total={})", pagina, tamanho, pautas.getTotalElements());
        return pautas;
    }

    public Pauta buscarPorId(Long id) throws NotFoundException {
        LOGGER.info("Iniciando - buscar pauta com o id: {}", id);

        Pauta pauta = pautaRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.info("Encerrado - buscar pauta com o id: {}. Erro ao buscar pauta", id);
                    return new NotFoundException("Pauta de id: %s não encontrada", id);
                });

        LOGGER.info("Encerrado - buscar pauta com o id: {}", id);

        return pauta;
    }

}
