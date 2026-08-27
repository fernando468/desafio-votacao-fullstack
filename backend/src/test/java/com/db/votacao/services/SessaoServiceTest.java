package com.db.votacao.services;

import com.db.votacao.dtos.requests.SessaoRequestDTO;
import com.db.votacao.dtos.responses.SessaoResponseDTO;
import com.db.votacao.exceptions.BadRequestException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Sessao;
import com.db.votacao.repositories.PautaRepository;
import com.db.votacao.repositories.SessaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {
    @InjectMocks
    private SessaoService sessaoService;

    @Mock
    private PautaService pautaService;

    @Mock
    private SessaoRepository sessaoRepository;

    @Test
    public void deveCriarSessaoComSucesso() throws NotFoundException, BadRequestException {
        SessaoRequestDTO sessaoRequestDTO = new SessaoRequestDTO(1L, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));

        Pauta pauta = new Pauta();
        pauta.setTitulo("Título");
        pauta.setDescricao("Descrição");

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setDataFim(LocalDateTime.now());

        when(pautaService.buscarPorId(1L)).thenReturn(pauta);
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);

        SessaoResponseDTO sessaoCriada = sessaoService.criarSessao(sessaoRequestDTO);

        assertNotNull(sessaoCriada);
        verify(sessaoRepository).save(any(Sessao.class));
    }

    @Test
    public void deveRetornarErroAoCriarPautaComDataFimAnteriorADataInicio() throws NotFoundException {
        SessaoRequestDTO sessaoRequestDTO = new SessaoRequestDTO(1L, LocalDateTime.now().plusWeeks(1), LocalDateTime.now());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> sessaoService.criarSessao(sessaoRequestDTO));

        assertEquals("Data de fim não pode ser anterior a data de ínicio", exception.getMessage());
        verify(pautaService, never()).buscarPorId(1L);
        verify(sessaoRepository, never()).save(any(Sessao.class));
    }

    @Test
    public void deveRetornarErroAoCriarSessaoComPautaNaoExistente() throws NotFoundException {
        SessaoRequestDTO sessaoRequestDTO = new SessaoRequestDTO(1L, LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));

        when(pautaService.buscarPorId(1L)).thenThrow(new NotFoundException("Pauta não encontrada"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> sessaoService.criarSessao(sessaoRequestDTO));

        assertEquals("Pauta não encontrada", exception.getMessage());
        verify(sessaoRepository, never()).save(any(Sessao.class));
    }

    @Test
    public void deveBuscarSessaoPorIdComSucesso() throws NotFoundException {
        Pauta pauta = new Pauta();
        pauta.setTitulo("Título");
        pauta.setDescricao("Descrição");

        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setPauta(pauta);
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setDataFim(LocalDateTime.now());

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        Sessao sessaoEncontrada = sessaoService.buscarSessao(1L);

        assertNotNull(sessaoEncontrada);
        verify(sessaoRepository).findById(1L);
    }

    @Test
    public void deveRetornarErroAoBuscarSessaoComIdNaoExistente() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> sessaoService.buscarSessao(1L));

        assertEquals("Sessão não encontrada", exception.getMessage());
        verify(sessaoRepository).findById(1L);
    }
}
