package com.db.votacao.services;

import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.dtos.responses.PautaResponseDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.mappers.AssociadoMapper;
import com.db.votacao.mappers.PautaMapper;
import com.db.votacao.models.Associado;
import com.db.votacao.models.Pauta;
import com.db.votacao.repositories.AssociadoRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociadoService extends PaginacaoService {
    private final AssociadoRepository associadoRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AssociadoService.class.getName());

    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public AssociadoResponseDTO criarAssociado(@Valid AssociadoRequestDTO associadoRequestDTO) throws ConflictException {
        String cpfAssociadoLog = associadoRequestDTO.cpf().substring(0, 3);

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
                    return new NotFoundException("Erro ao buscar usuário de id: %s", id);
                });

        LOGGER.info("Encerrado - buscar associado com o id: {}", id);
        return associado;
    }

    public Associado getReferenceById(Long id) {
        return associadoRepository.getReferenceById(id);
    }

    public Page<AssociadoResponseDTO> buscarAssociadoPaginado(Integer pagina, Integer tamanho) {
        LOGGER.info("Iniciando - buscar associados (pagina={}, tamanho={})", pagina, tamanho);
        Page<AssociadoResponseDTO> associados = paginar(Associado.class, pagina, tamanho, "id", Sort.Direction.ASC, AssociadoMapper::toResponseDTO);
        LOGGER.info("Encerrado - buscar associados (pagina={}, tamanho={}, total={})", pagina, tamanho, associados.getTotalElements());
        return associados;
    }
}
