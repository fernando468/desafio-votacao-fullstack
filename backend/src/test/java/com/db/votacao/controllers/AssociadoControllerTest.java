package com.db.votacao.controllers.v1;

import com.db.votacao.dtos.requests.AssociadoRequestDTO;
import com.db.votacao.dtos.responses.AssociadoResponseDTO;
import com.db.votacao.services.AssociadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AssociadoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AssociadoController associadoController;

    @Mock
    private AssociadoService associadoService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(associadoController)
                .setValidator(new LocalValidatorFactoryBean())
                .build();
    }

    @Test
    public void deveCriarAssociadoComSucessoViaRequisicao() throws Exception {
        AssociadoResponseDTO associadoResponseDTO = new AssociadoResponseDTO(1L, "47286817000");

        when(associadoService.criarAssociado(any(AssociadoRequestDTO.class))).thenReturn(associadoResponseDTO);

        mockMvc.perform(post("/associados/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cpf\":\"47286817000\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cpf").value("47286817000"));

        verify(associadoService).criarAssociado(any(AssociadoRequestDTO.class));
    }
}
