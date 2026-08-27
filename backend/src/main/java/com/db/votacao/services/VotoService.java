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
import org.springframework.stereotype.Service;

@Service
public class VotoService {
    private final VotoRepository votoRepository;
    private final SessaoService sessaoService;
    private final AssociadoService associadoService;

    public VotoService(VotoRepository votoRepository,
                       SessaoService sessaoService,
                       AssociadoService associadoService) {
        this.votoRepository = votoRepository;
        this.sessaoService = sessaoService;
        this.associadoService = associadoService;
    }

    public VotoResponseDTO votar(VotoRequestDTO votoRequestDTO) throws NotFoundException, ConflictException {
        Sessao sessao = sessaoService.buscarSessao(votoRequestDTO.sessaoId());
        Associado associado = associadoService.buscarPorId(votoRequestDTO.associadoId());
        boolean associadoJaVotou = votoRepository.existsByAssociadoIdAndSessaoId(votoRequestDTO.associadoId(), votoRequestDTO.sessaoId());


        if (sessao.isAberta() || associadoJaVotou) {
            throw new ConflictException();
        }

        Voto votoCriado = votoRepository.save(VotoMapper.toEntity(votoRequestDTO, associado, sessao));

        return VotoMapper.toResponseDTO(votoCriado);
    }

}
