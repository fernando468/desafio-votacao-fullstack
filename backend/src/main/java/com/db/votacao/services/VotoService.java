package com.db.votacao.services;

import com.db.votacao.dtos.requests.VotoRequestDTO;
import com.db.votacao.dtos.responses.VotoResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.VotoMapper;
import com.db.votacao.models.Associado;
import com.db.votacao.models.Sessao;
import com.db.votacao.models.Voto;
import com.db.votacao.repositories.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
    private final VotoRepository votoRepository;
    private final SessaoService sessaoService;
    private final AssociadoService associadoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(VotoService.class.getName());

    public VotoService(VotoRepository votoRepository,
                       SessaoService sessaoService,
                       AssociadoService associadoService) {
        this.votoRepository = votoRepository;
        this.sessaoService = sessaoService;
        this.associadoService = associadoService;
    }

    public VotoResponseDTO criarVoto(VotoRequestDTO votoRequestDTO) throws NotFoundException, ConflictException {
        LOGGER.info("Iniciando - criar voto: {}", votoRequestDTO);

        Sessao sessao = sessaoService.buscarSessao(votoRequestDTO.sessaoId());
        Associado associado = associadoService.buscarPorId(votoRequestDTO.associadoId());
        boolean associadoJaVotou = votoRepository.existsByAssociadoIdAndSessaoId(votoRequestDTO.associadoId(), votoRequestDTO.sessaoId());


        if (!sessao.isAberta()) {
            LOGGER.info("Encerrado - criar voto: {}. Erro sessão não esta aberta", votoRequestDTO);
            throw new ConflictException("Sessão de id: %s não esta aberta para votação", votoRequestDTO.sessaoId());
        }

        if (associadoJaVotou) {
            LOGGER.info("Encerrado - criar voto: {}. Associado já votou", votoRequestDTO);
            throw new ConflictException("Associado de id: %s já votou nessa pauta", votoRequestDTO.associadoId());
        }

        Voto votoCriado = votoRepository.save(VotoMapper.toEntity(votoRequestDTO, associado, sessao));

        LOGGER.info("Encerrado - criar voto: {}", votoRequestDTO);

        return VotoMapper.toResponseDTO(votoCriado);
    }

}
