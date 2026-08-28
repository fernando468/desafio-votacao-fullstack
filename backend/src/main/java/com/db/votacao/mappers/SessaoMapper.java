package com.db.votacao.mappers;

import com.db.votacao.dtos.requests.SessaoRequestDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Sessao;

import java.time.LocalDateTime;

public class SessaoMapper {
    public static Sessao toEntity(SessaoRequestDTO sessaoRequestDTO, Pauta pauta, LocalDateTime dataFinal) {
        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataInicio(sessaoRequestDTO.dataInicio());
        sessao.setDataFim(dataFinal);
        return sessao;
    }

    public static SessaoResponseDTO toResponseDTO(Sessao sessao) {
        return new SessaoResponseDTO(
                sessao.getId(),
                PautaMapper.toResponseDTO(sessao.getPauta()),
                sessao.getDataInicio(),
                sessao.getDataFim(),
                sessao.isAberta()
        );
    }
}
