package com.db.votacao.services;

import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.exceptions.ConflictException;
import com.db.votacao.exceptions.NotFoundException;
import com.db.votacao.models.Associado;
import com.db.votacao.repositories.AssociadoRepository;
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
public class AssociadoServiceTest {
    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository associadoRepository;

    @Test
    public void deveCriarAssociadoComSucesso() throws ConflictException {
        AssociadoRequestDTO associadoRequestDTO = new AssociadoRequestDTO("39591736010");

        Associado associado = new Associado();
        associado.setId(1L);
        associado.setCpf("39591736010");

        when(associadoRepository.findByCpf("39591736010")).thenReturn(Optional.empty());
        when(associadoRepository.save(any(Associado.class))).thenReturn(associado);

        AssociadoResponseDTO associadoResponseDTO = associadoService.criarAssociado(associadoRequestDTO);

        assertNotNull(associadoResponseDTO);
        verify(associadoRepository).findByCpf("39591736010");
        verify(associadoRepository).save(any(Associado.class));
    }

    @Test
    public void deveRetornarErroAoCriarAssociadoComCpfExistente() throws ConflictException {
        AssociadoRequestDTO associadoRequestDTO = new AssociadoRequestDTO("39591736010");

        Associado associado = new Associado();
        associado.setId(1L);
        associado.setCpf("39591736010");

        when(associadoRepository.findByCpf("39591736010")).thenReturn(Optional.of(associado));

        ConflictException exception = assertThrows(ConflictException.class, () -> associadoService.criarAssociado(associadoRequestDTO));

        assertEquals("Erro ao criar associado", exception.getMessage());
        verify(associadoRepository).findByCpf("39591736010");
        verify(associadoRepository, never()).save(any(Associado.class));
    }

    @Test
    public void deveBuscarAssociadoComSucesso() throws NotFoundException {
        Associado associado = new Associado();
        associado.setId(1L);
        associado.setCpf("39591736010");

        when(associadoRepository.findById(1L)).thenReturn(Optional.of(associado));

        Associado associadoBuscado = associadoService.buscarPorId(1L);

        assertNotNull(associadoBuscado);
        verify(associadoRepository).findById(1L);
    }

    @Test
    public void deveRetornarErroQuandoAssociadoNaoEncontrado() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> associadoService.buscarPorId(1L));

        assertEquals("Erro ao buscar usuário", exception.getMessage());
        verify(associadoRepository).findById(1L);
    }
}
