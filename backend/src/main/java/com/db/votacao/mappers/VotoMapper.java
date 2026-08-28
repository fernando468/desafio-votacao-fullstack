package com.db.votacao.mappers;

import com.db.votacao.dtos.requests.VotoRequestDTO;
import com.db.votacao.dtos.responses.VotoResponseDTO;
import com.db.votacao.models.Associado;
import com.db.votacao.models.Sessao;
import com.db.votacao.models.Voto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VotoMapper {
    public static Voto toEntity(VotoRequestDTO votoRequestDTO, Associado associado, Sessao sessao) {
        Voto voto = new Voto();
        voto.setSessao(sessao);
        voto.setAssociado(associado);
        voto.setTipoVoto(votoRequestDTO.tipoVoto());
        voto.setDataVotacao(LocalDate.now());
        return voto;
    }

    public static VotoResponseDTO toResponseDTO(Voto voto) {
        return new VotoResponseDTO(
                voto.getId(),
                voto.getTipoVoto(),
                AssociadoMapper.toResponseDTO(voto.getAssociado()),
                SessaoMapper.toResponseDTO(voto.getSessao())
        );
    }
}
