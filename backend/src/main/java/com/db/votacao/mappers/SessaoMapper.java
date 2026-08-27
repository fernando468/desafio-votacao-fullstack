package com.db.votacao.mappers;

import com.db.votacao.dtos.requests.SessaoRequestDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Sessao;

public class SessaoMapper {
    public static Sessao toEntity(SessaoRequestDTO sessaoRequestDTO, Pauta pauta) {
        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataInicio(sessaoRequestDTO.dataInicio());
        sessao.setDataFim(sessaoRequestDTO.dataFim());
        return sessao;
    }

    public static SessaoResponseDTO toResponseDTO(Sessao sessao) {
        return new SessaoResponseDTO(
                PautaMapper.toResponseDTO(sessao.getPauta()),
                sessao.getDataInicio(),
                sessao.getDataFim(),
                sessao.isAberta()
        );
    }
}
