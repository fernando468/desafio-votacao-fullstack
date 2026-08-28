package com.db.votacao.services;

import com.db.votacao.dtos.requests.VotoRequestDTO;
import com.db.votacao.dtos.responses.VotoResponseDTO;
import com.db.votacao.enums.TipoVotoEnum;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.models.Associado;
import com.db.votacao.models.Pauta;
import com.db.votacao.models.Sessao;
import com.db.votacao.models.Voto;
import com.db.votacao.repositories.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {
    @InjectMocks
    private VotoService votoService;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private AssociadoService associadoService;

    @Mock
    private VotoRepository votoRepository;

    @Test
    public void deveCriarVotoQuandoSessaoEstaAberta() throws NotFoundException, ConflictException {
        Long sessaoId = 1L;
        Long associadoId = 1L;

        VotoRequestDTO request = new VotoRequestDTO(sessaoId, associadoId, TipoVotoEnum.SIM);

        Pauta pauta = new Pauta();
        pauta.setTitulo("Título");
        pauta.setDescricao("Descrição");
        pauta.setId(1L);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataInicio(LocalDateTime.of(2026, Month.JANUARY, 1, 0, 0));
        sessao.setDataFim(LocalDateTime.now().plusYears(1));

        Associado associado = new Associado();
        associado.setId(associadoId);
        associado.setCpf("55086890012");

        Voto votoSalvo = new Voto();
        votoSalvo.setId(1L);
        votoSalvo.setDataVotacao(LocalDateTime.now());
        votoSalvo.setTipoVoto(request.tipoVoto());
        votoSalvo.setAssociado(associado);
        votoSalvo.setSessao(sessao);

        when(sessaoService.buscarSessao(sessaoId)).thenReturn(sessao);
        when(associadoService.buscarPorId(associadoId)).thenReturn(associado);
        when(votoRepository.existsByAssociadoIdAndSessaoId(associadoId, sessaoId)).thenReturn(false);
        when(votoRepository.save(any(Voto.class))).thenReturn(votoSalvo);

        VotoResponseDTO response = votoService.criarVoto(request);

        assertNotNull(response);

        verify(sessaoService).buscarSessao(sessaoId);
        verify(associadoService).buscarPorId(associadoId);
        verify(votoRepository).existsByAssociadoIdAndSessaoId(associadoId, sessaoId);
        verify(votoRepository).save(any(Voto.class));
    }

    @Test
    public void deveRetornarErroQuandoSessaoEstaFechada() throws NotFoundException {
        Long sessaoId = 1L;
        Long associadoId = 1L;

        VotoRequestDTO request = new VotoRequestDTO(sessaoId, associadoId, TipoVotoEnum.SIM);

        Pauta pauta = new Pauta();
        pauta.setTitulo("Título");
        pauta.setDescricao("Descrição");
        pauta.setId(1L);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataInicio(LocalDateTime.of(2026, Month.JANUARY, 1, 0, 0));
        sessao.setDataFim(LocalDateTime.of(2026, Month.JANUARY, 30, 0, 0));

        Associado associado = new Associado();
        associado.setId(associadoId);
        associado.setCpf("55086890012");

        when(sessaoService.buscarSessao(sessaoId)).thenReturn(sessao);
        when(associadoService.buscarPorId(associadoId)).thenReturn(associado);
        when(votoRepository.existsByAssociadoIdAndSessaoId(associadoId, sessaoId)).thenReturn(false);

        ConflictException exception = assertThrows(ConflictException.class, () -> votoService.criarVoto(request));

        assertEquals("Sessão de id: 1 não esta aberta para votação", exception.getMessage());
        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    public void deveRetornarErroQuandoAssociadoJaVotou() throws NotFoundException {
        Long sessaoId = 1L;
        Long associadoId = 1L;

        VotoRequestDTO request = new VotoRequestDTO(sessaoId, associadoId, TipoVotoEnum.SIM);

        Pauta pauta = new Pauta();
        pauta.setTitulo("Título");
        pauta.setDescricao("Descrição");
        pauta.setId(1L);

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataInicio(LocalDateTime.of(2026, Month.JANUARY, 1, 0, 0));
        sessao.setDataFim(LocalDateTime.now().plusYears(1));

        Associado associado = new Associado();
        associado.setId(associadoId);
        associado.setCpf("55086890012");

        when(sessaoService.buscarSessao(sessaoId)).thenReturn(sessao);
        when(associadoService.buscarPorId(associadoId)).thenReturn(associado);
        when(votoRepository.existsByAssociadoIdAndSessaoId(associadoId, sessaoId)).thenReturn(true);

        ConflictException exception = assertThrows(ConflictException.class, () -> votoService.criarVoto(request));

        assertEquals("Associado de id: 1 já votou nessa pauta", exception.getMessage());
        verify(votoRepository, never()).save(any(Voto.class));
    }

}
