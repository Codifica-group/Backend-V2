package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.raca.*;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.RacaDTO;
import codifica.eleve.interfaces.dtoAdapters.RacaDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RacaController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RacaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private CreateRacaUseCase createRacaUseCase;
    @MockBean
    private ListRacaUseCase listRacaUseCase;
    @MockBean
    private FindRacaByIdUseCase findRacaByIdUseCase;
    @MockBean
    private UpdateRacaUseCase updateRacaUseCase;
    @MockBean
    private DeleteRacaUseCase deleteRacaUseCase;
    @MockBean
    private FindRacaByNomeUseCase findRacaByNomeUseCase;
    @MockBean
    private FindRacasByNomeSemelhanteUseCase findRacasByNomeSemelhanteUseCase;

    @MockBean
    private RacaDtoMapper racaDtoMapper;

    private static RacaDTO racaPadraoDTO;
    private static Raca racaDomainPadrao;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static String asJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() {
        racaPadraoDTO = new RacaDTO();
        racaPadraoDTO.setId(1);
        racaPadraoDTO.setNome("Pug");
        racaPadraoDTO.setPorteId(1);

        Porte porte = new Porte("Pequeno");
        porte.setId(new Id(1));

        racaDomainPadrao = new Raca("Pug", porte);
        racaDomainPadrao.setId(new Id(1));
    }

    @Test
    @Order(1)
    void deveCadastrarRaca() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Raça cadastrada com sucesso.");
        resposta.put("id", 1);

        when(racaDtoMapper.toDomain(any(RacaDTO.class))).thenReturn(racaDomainPadrao);
        when(createRacaUseCase.execute(any(Raca.class))).thenReturn(resposta);

        mvc.perform(post("/api/racas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(racaPadraoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Raça cadastrada com sucesso."))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(2)
    void deveFalharNoCadastro_RacaJaExiste() throws Exception {
        when(racaDtoMapper.toDomain(any(RacaDTO.class))).thenReturn(racaDomainPadrao);
        when(createRacaUseCase.execute(any(Raca.class)))
                .thenThrow(new ConflictException("Impossível cadastrar duas raças com o mesmo nome."));

        mvc.perform(post("/api/racas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(racaPadraoDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Impossível cadastrar duas raças com o mesmo nome."));
    }

    @Test
    @Order(3)
    void deveListarRacas() throws Exception {
        when(listRacaUseCase.execute()).thenReturn(List.of(racaDomainPadrao));
        when(racaDtoMapper.toDto(any(Raca.class))).thenReturn(racaPadraoDTO);

        mvc.perform(get("/api/racas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Pug"))
                .andExpect(jsonPath("$[0].porteId").value(1));
    }

    @Test
    @Order(4)
    void deveRetornar204_ListaVazia() throws Exception {
        when(listRacaUseCase.execute()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/racas"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    void deveAtualizarRaca() throws Exception {
        when(racaDtoMapper.toDomain(any(RacaDTO.class))).thenReturn(racaDomainPadrao);
        when(updateRacaUseCase.execute(eq(1), any(Raca.class)))
                .thenReturn("Raça atualizada com sucesso.");

        mvc.perform(put("/api/racas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(racaPadraoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Raça atualizada com sucesso."));
    }

    @Test
    @Order(6)
    void deveFalharNaAtualizacao_RacaInexistente() throws Exception {
        when(racaDtoMapper.toDomain(any(RacaDTO.class))).thenReturn(racaDomainPadrao);
        when(updateRacaUseCase.execute(eq(99), any(Raca.class)))
                .thenThrow(new NotFoundException("Raça não encontrada."));

        mvc.perform(put("/api/racas/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(racaPadraoDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Raça não encontrada."));
    }

    @Test
    @Order(7)
    void deveDeletarRaca() throws Exception {
        Mockito.doNothing().when(deleteRacaUseCase).execute(1);

        mvc.perform(delete("/api/racas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    void deveFalharAoDeletar_RacaPossuiPets() throws Exception {
        Mockito.doThrow(new ConflictException("Não é possível deletar raças que possuem pets cadastrados."))
                .when(deleteRacaUseCase).execute(1);

        mvc.perform(delete("/api/racas/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Não é possível deletar raças que possuem pets cadastrados."));
    }

    @Test
    @Order(9)
    void deveFalharAoDeletar_RacaInexistente() throws Exception {
        Mockito.doThrow(new NotFoundException("Raça não encontrada."))
                .when(deleteRacaUseCase).execute(99);

        mvc.perform(delete("/api/racas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Raça não encontrada."));
    }
}
