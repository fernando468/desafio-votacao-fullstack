package com.db.votacao.services;

import com.db.votacao.dtos.requests.PautaRequestDTO;
import com.db.votacao.dtos.responses.PautaResponseDTO;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Voto;
import com.db.votacao.repositories.PautaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {
    @InjectMocks
    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    public void deveCriarPautaComSucesso() {
        PautaRequestDTO pautaRequestDTO = new PautaRequestDTO("Título", "Descrição");

        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Título");
        pauta.setDescricao("Descricão");


        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        PautaResponseDTO pautaResponseDTO = pautaService.criarPauta(pautaRequestDTO);

        assertNotNull(pautaResponseDTO);
        verify(pautaRepository).save(any(Pauta.class));
    }

    @Test
    public void deveBuscarPautaPorIdComSucesso() throws NotFoundException {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setTitulo("Título");
        pauta.setDescricao("Descricão");

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        Pauta pautaEncontrada = pautaService.buscarPorId(1L);
        assertNotNull(pautaEncontrada);

        verify(pautaRepository).findById(1L);
    }

    @Test
    public void deveRetornarErroAoBuscarPautaNaoExistente() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> pautaService.buscarPorId(1L));

        assertEquals("Pauta de id: 1 não encontrada", exception.getMessage());
        verify(pautaRepository, never()).save(any(Pauta.class));
        verify(pautaRepository).findById(1L);
    }
}
