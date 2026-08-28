package com.db.votacao.services;

import com.db.votacao.dtos.requests.SessaoRequestDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.exceptions.BadRequestException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.SessaoMapper;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Sessao;
import com.db.votacao.repositories.SessaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SessaoService extends PaginacaoService {
    private final SessaoRepository sessaoRepository;
    private final PautaService pautaService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoService.class.getName());

    public SessaoService(SessaoRepository sessaoRepository, PautaService pautaService) {
        this.sessaoRepository = sessaoRepository;
        this.pautaService = pautaService;
    }

    public SessaoResponseDTO criarSessao(SessaoRequestDTO sessaoRequestDTO) throws NotFoundException, BadRequestException {
        LOGGER.info("Iniciando - criar sessão para a pauta de id: {}", sessaoRequestDTO.pautaId());
        if (sessaoRequestDTO.dataFim().isBefore(sessaoRequestDTO.dataInicio())) {
            throw new BadRequestException("Data de fim não pode ser anterior a data de ínicio");
        }

        Pauta pauta = pautaService.buscarPorId(sessaoRequestDTO.pautaId());
        Sessao sessao = SessaoMapper.toEntity(sessaoRequestDTO, pauta);
        Sessao sessaoCriada = sessaoRepository.save(sessao);

        LOGGER.info("Encerrado - criar sessão para a pauta de id: {}", sessaoRequestDTO.pautaId());

        return SessaoMapper.toResponseDTO(sessaoCriada);
    }

    public Page<SessaoResponseDTO> buscarSessaoPaginado(int pagina, int tamanho) {
        LOGGER.info("Iniciando - buscar sessão (pagina={}, tamanho={})", pagina, tamanho);
        Page<SessaoResponseDTO> sessao = paginar(Sessao.class, pagina, tamanho, "id", Sort.Direction.ASC, SessaoMapper::toResponseDTO);
        LOGGER.info("Encerrado - buscar sessão (pagina={}, tamanho={}, total={})", pagina, tamanho, sessao.getTotalElements());
        return sessao;
    }

    public Sessao buscarSessao(Long id) throws NotFoundException {
        LOGGER.info("Iniciando - buscar sessão com o id: {}", id);

        Sessao sessao = sessaoRepository.findById(id).orElseThrow(() -> {
            LOGGER.info("Encerrado - buscar sessão com o id: {}. Sessão não encontrada", id);
            return new NotFoundException("Sessão de id: %s não encontrada", id);
        });

        LOGGER.info("Encerrado - buscar sessão com o id: {}", id);

        return sessao;
    }
}
