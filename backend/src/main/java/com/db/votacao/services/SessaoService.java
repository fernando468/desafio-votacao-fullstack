package com.db.votacao.services;

import com.db.votacao.dtos.requests.SessaoRequestDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.SessaoMapper;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Sessao;
import com.db.votacao.repositories.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessaoService {
    private final SessaoRepository sessaoRepository;
    private final PautaService pautaService;

    public SessaoService(SessaoRepository sessaoRepository, PautaService pautaService) {
        this.sessaoRepository = sessaoRepository;
        this.pautaService = pautaService;
    }

    public SessaoResponseDTO abrirSessao(SessaoRequestDTO sessaoRequestDTO) throws NotFoundException {
        Pauta pauta = pautaService.findById(sessaoRequestDTO.pautaId());
        Sessao sessao = SessaoMapper.toEntity(sessaoRequestDTO, pauta);
        Sessao sessaoCriada = sessaoRepository.save(sessao);
        return SessaoMapper.toResponseDTO(sessaoCriada);
    }


    public Sessao buscarSessao(Long id) throws NotFoundException {
        return sessaoRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }
}
