package com.db.votacao.services;

import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.models.Pauta;
import com.db.votacao.repositories.PautaRepository;
import org.springframework.stereotype.Service;

@Service
public class PautaService {
    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Pauta findById(Long id) throws NotFoundException {
        return pautaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }
}
