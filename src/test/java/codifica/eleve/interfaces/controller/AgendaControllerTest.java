package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.agenda.*;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularLucroUseCase;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularServicoUseCase;
import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.agenda.Filtro;
import codifica.eleve.core.domain.agenda.calculadora.SugestaoServico;
import codifica.eleve.core.domain.agenda.deslocamento.Deslocamento;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.shared.*;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.*;
import codifica.eleve.interfaces.dtoAdapters.AgendaDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.FiltroDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.SugestaoServicoDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

@WebMvcTest(AgendaController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AgendaControllerTest {

    @Autowired
    private MockMvc mvc;

    // --- MOCKS DE SEGURANÇA ---
    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    // --- USE CASES ---
    @MockBean
    private CreateAgendaUseCase createAgendaUseCase;
    @MockBean
    private FindAgendaByIdUseCase findAgendaByIdUseCase;
    @MockBean
    private ListAgendaUseCase listAgendaUseCase;
    @MockBean
    private UpdateAgendaUseCase updateAgendaUseCase;
    @MockBean
    private DeleteAgendaUseCase deleteAgendaUseCase;
    @MockBean
    private CalcularServicoUseCase calcularServicoUseCase;
    @MockBean
    private FilterAgendaUseCase filterAgendaUseCase;
    @MockBean
    private DisponibilidadeAgendaUseCase disponibilidadeAgendaUseCase;
    @MockBean
    private CalcularLucroUseCase calcularLucroUseCase;
    @MockBean
    private FindFutureAgendasByPetIdUseCase findFutureAgendasByPetIdUseCase;

    // --- MAPPERS ---
    @MockBean
    private AgendaDtoMapper agendaDtoMapper;
    @MockBean
    private SugestaoServicoDtoMapper sugestaoServicoDtoMapper;
    @MockBean
    private FiltroDtoMapper filtroDtoMapper;

    private static AgendaDTO agendaPadraoDTO;
    private static Agenda agendaDomainPadrao;
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static String asJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() {
        // DTO
        agendaPadraoDTO = new AgendaDTO();
        agendaPadraoDTO.setId(1);
        agendaPadraoDTO.setPetId(1);
        agendaPadraoDTO.setDataHoraInicio(LocalDateTime.of(2023, 10, 15, 10, 0));
        agendaPadraoDTO.setDataHoraFim(LocalDateTime.of(2023, 10, 15, 11, 0));
        agendaPadraoDTO.setValorTotal(BigDecimal.valueOf(200.0));

        ServicoDTO servicoDTO = new ServicoDTO();
        servicoDTO.setId(1);
        servicoDTO.setValor(BigDecimal.valueOf(100.0));
        agendaPadraoDTO.setServicos(List.of(servicoDTO));

        // Domain
        Pet pet = new Pet("Rex", new Raca("Raça", new Porte("Médio")), new Cliente("Cli", new Telefone("11999999999"), new Endereco("00000000", "R", "1", "B", "C", "C")));
        pet.setId(new Id(1));

        List<Servico> servicos = List.of(new Servico("Banho", new ValorMonetario(BigDecimal.valueOf(100.0))));

        agendaDomainPadrao = new Agenda(
                pet,
                servicos,
                new ValorMonetario(BigDecimal.ZERO),
                new Periodo(LocalDateTime.of(2023, 10, 15, 10, 0), LocalDateTime.of(2023, 10, 15, 11, 0))
        );
        agendaDomainPadrao.setId(new Id(1));
    }

    // ---------- POST ----------
    @Test
    @Order(1)
    void deveCadastrarAgenda() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Agenda cadastrada com sucesso.");
        resposta.put("id", 1);

        when(agendaDtoMapper.toDomain(any(AgendaDTO.class))).thenReturn(agendaDomainPadrao);
        when(createAgendaUseCase.execute(any(Agenda.class))).thenReturn(resposta);

        mvc.perform(post("/api/agendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(agendaPadraoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Agenda cadastrada com sucesso."))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(2)
    void deveFalharAoCadastrarAgenda_ConflitoDeHorario() throws Exception {
        when(agendaDtoMapper.toDomain(any(AgendaDTO.class))).thenReturn(agendaDomainPadrao);
        when(createAgendaUseCase.execute(any(Agenda.class)))
                .thenThrow(new ConflictException("Já existe outro agendamento no período informado."));

        mvc.perform(post("/api/agendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(agendaPadraoDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Já existe outro agendamento no período informado."));
    }

    // ---------- GET ALL ----------
    @Test
    @Order(3)
    void deveListarTodasAgendas() throws Exception {
        Pagina<Agenda> paginaMock = new Pagina<>(List.of(agendaDomainPadrao), 1);

        when(listAgendaUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaMock);
        when(agendaDtoMapper.toDto(any(Agenda.class))).thenReturn(agendaPadraoDTO);

        mvc.perform(get("/api/agendas")
                        .param("offset", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dados").isArray())
                .andExpect(jsonPath("$.dados[0].id").value(1))
                .andExpect(jsonPath("$.dados[0].valorTotal").value(200.0));
    }

    @Test
    @Order(4)
    void deveRetornar204_ListaVazia() throws Exception {
        Pagina<Agenda> paginaVazia = new Pagina<>(Collections.emptyList(), 0);

        when(listAgendaUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaVazia);

        mvc.perform(get("/api/agendas"))
                .andExpect(status().isNoContent());
    }

    // ---------- GET by ID ----------
    @Test
    @Order(5)
    void deveBuscarAgendaPorId() throws Exception {
        when(findAgendaByIdUseCase.execute(1)).thenReturn(agendaDomainPadrao);
        when(agendaDtoMapper.toDto(agendaDomainPadrao)).thenReturn(agendaPadraoDTO);

        mvc.perform(get("/api/agendas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.valorTotal").value(200.0));
    }

    @Test
    @Order(6)
    void deveRetornar404_AgendaNaoEncontrada() throws Exception {
        when(findAgendaByIdUseCase.execute(99))
                .thenThrow(new NotFoundException("Agenda não encontrada."));

        mvc.perform(get("/api/agendas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Agenda não encontrada."));
    }

    // ---------- PUT ----------
    @Test
    @Order(7)
    void deveAtualizarAgenda() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Agenda atualizada com sucesso.");
        resposta.put("id", 1);

        when(agendaDtoMapper.toDomain(any(AgendaDTO.class))).thenReturn(agendaDomainPadrao);
        when(updateAgendaUseCase.execute(eq(1), any(Agenda.class))).thenReturn(resposta);

        mvc.perform(put("/api/agendas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(agendaPadraoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Agenda atualizada com sucesso."));
    }

    @Test
    @Order(8)
    void deveFalharAoAtualizarAgenda_ConflitoDeHorario() throws Exception {
        when(agendaDtoMapper.toDomain(any(AgendaDTO.class))).thenReturn(agendaDomainPadrao);
        when(updateAgendaUseCase.execute(eq(1), any(Agenda.class)))
                .thenThrow(new ConflictException("Já existe outro agendamento no período informado."));

        mvc.perform(put("/api/agendas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(agendaPadraoDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Já existe outro agendamento no período informado."));
    }

    // ---------- DELETE ----------
    @Test
    @Order(9)
    void deveDeletarAgenda() throws Exception {
        Mockito.doNothing().when(deleteAgendaUseCase).execute(1);

        mvc.perform(delete("/api/agendas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void deveFalharAoDeletarAgendaNaoEncontrada() throws Exception {
        doThrow(new NotFoundException("Agenda não encontrada."))
                .when(deleteAgendaUseCase).execute(99);

        mvc.perform(delete("/api/agendas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Agenda não encontrada."));
    }

    // ---------- FILTRAR ----------
    @Test
    @Order(11)
    void deveFiltrarAgendasComSucesso() throws Exception {
        FiltroDTO filtroDTO = new FiltroDTO();
        filtroDTO.setDataInicio(LocalDate.of(2023, 10, 15));
        filtroDTO.setDataFim(LocalDate.of(2023, 10, 16));

        Pagina<Agenda> paginaMock = new Pagina<>(List.of(agendaDomainPadrao), 1);

        when(filtroDtoMapper.toDomain(any(FiltroDTO.class))).thenReturn(new Filtro());
        when(filterAgendaUseCase.execute(any(Filtro.class), any(Integer.class), any(Integer.class)))
                .thenReturn(paginaMock);
        when(agendaDtoMapper.toDto(any(Agenda.class))).thenReturn(agendaPadraoDTO);

        mvc.perform(post("/api/agendas/filtrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(filtroDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dados[0].id").value(1));
    }

    @Test
    @Order(12)
    void deveRetornar204_FiltroSemResultados() throws Exception {
        FiltroDTO filtroDTO = new FiltroDTO();
        Pagina<Agenda> paginaVazia = new Pagina<>(Collections.emptyList(), 0);

        when(filtroDtoMapper.toDomain(any(FiltroDTO.class))).thenReturn(new Filtro());
        when(filterAgendaUseCase.execute(any(Filtro.class), any(Integer.class), any(Integer.class)))
                .thenReturn(paginaVazia);

        mvc.perform(post("/api/agendas/filtrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(filtroDTO)))
                .andExpect(status().isNoContent());
    }

    // ---------- CALCULAR ----------
    @Test
    @Order(13)
    void deveCalcularLucro() throws Exception {
        LucroDTO lucroRetorno = new LucroDTO(new BigDecimal("300.0"), new BigDecimal("500.0"), new BigDecimal("200.0"));
        when(calcularLucroUseCase.execute(any(Periodo.class))).thenReturn(lucroRetorno);
        Map<String, Object> payload = new HashMap<>();
        payload.put("dataInicio", "2023-10-01");
        payload.put("dataFim", "2023-10-31");

        mvc.perform(post("/api/agendas/calcular/lucro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(300.0));
    }

    @Test
    @Order(14)
    void deveCalcularServico() throws Exception {
        // Request: CalcularServicoRequestDTO
        CalcularServicoRequestDTO requestDTO = new CalcularServicoRequestDTO();
        requestDTO.setPetId(1);
        ServicoDTO sDto = new ServicoDTO();
        sDto.setId(1);
        requestDTO.setServicos(List.of(sDto));

        // Response Domain: SugestaoServico
        SugestaoServico sugestao = new SugestaoServico(
                new BigDecimal("250.0"),
                List.of(),
                new Deslocamento(10.0, 5.0, 50.0)
        );

        // Response DTO: SugestaoServicoDTO
        SugestaoServicoDTO responseDTO = new SugestaoServicoDTO();
        responseDTO.setValor(new BigDecimal("250.0"));
        DeslocamentoResponseDTO deslocamentoResponseDTO = new DeslocamentoResponseDTO(new BigDecimal("10.0"), new BigDecimal("50.0"), new BigDecimal("5.0"));
        responseDTO.setDeslocamento(deslocamentoResponseDTO);

        when(calcularServicoUseCase.execute(eq(1), any(List.class))).thenReturn(sugestao);
        when(sugestaoServicoDtoMapper.toDto(sugestao)).thenReturn(responseDTO);

        mvc.perform(post("/api/agendas/calcular/servico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").value(250.0))
                .andExpect(jsonPath("$.deslocamento.valor").value(50.0));
    }
}
