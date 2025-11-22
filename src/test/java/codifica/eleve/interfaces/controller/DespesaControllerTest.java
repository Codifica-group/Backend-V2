package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.despesa.*;
import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.CategoriaProdutoDTO;
import codifica.eleve.interfaces.dto.DespesaDTO;
import codifica.eleve.interfaces.dto.ProdutoDTO;
import codifica.eleve.interfaces.dtoAdapters.DespesaDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(DespesaController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DespesaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private CreateDespesaUseCase createDespesaUseCase;
    @MockBean
    private ListDespesaUseCase listDespesaUseCase;
    @MockBean
    private FindDespesaByIdUseCase findDespesaByIdUseCase;
    @MockBean
    private UpdateDespesaUseCase updateDespesaUseCase;
    @MockBean
    private DeleteDespesaUseCase deleteDespesaUseCase;

    @MockBean
    private DespesaDtoMapper despesaDtoMapper;

    private static DespesaDTO despesaPadraoDTO;
    private static Despesa despesaDomainPadrao;
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private static String asJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1);
        produtoDTO.setNome("Shampoo");
        produtoDTO.setCategoriaId(1);
        CategoriaProdutoDTO categoriaDTO = new CategoriaProdutoDTO();
        categoriaDTO.setId(1);
        categoriaDTO.setNome("Insumo");
        produtoDTO.setCategoria(categoriaDTO);

        despesaPadraoDTO = new DespesaDTO();
        despesaPadraoDTO.setId(1);
        despesaPadraoDTO.setProdutoId(1);
        despesaPadraoDTO.setValor(new BigDecimal("200.0"));
        despesaPadraoDTO.setData(LocalDate.of(2025, 4, 18));
        despesaPadraoDTO.setProduto(produtoDTO);

        CategoriaProduto categoriaDomain = new CategoriaProduto("Insumo");
        categoriaDomain.setId(new Id(1));
        Produto produtoDomain = new Produto("Shampoo", categoriaDomain);
        produtoDomain.setId(new Id(1));

        despesaDomainPadrao = new Despesa(
                produtoDomain,
                new ValorMonetario(new BigDecimal("200.0")),
                LocalDate.of(2025, 4, 18)
        );
        despesaDomainPadrao.setId(new Id(1));
    }

    @Test
    @Order(1)
    void deveCadastrarDespesa() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Despesa cadastrada com sucesso.");
        resposta.put("id", 1);

        when(despesaDtoMapper.toDomain(any(DespesaDTO.class))).thenReturn(despesaDomainPadrao);
        when(createDespesaUseCase.execute(any(Despesa.class))).thenReturn(resposta);

        mvc.perform(post("/api/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(despesaPadraoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Despesa cadastrada com sucesso."))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(2)
    void deveFalharNoCadastro_ProdutoInexistente() throws Exception {
        when(despesaDtoMapper.toDomain(any(DespesaDTO.class)))
                .thenThrow(new NotFoundException("Produto não encontrado"));

        mvc.perform(post("/api/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(despesaPadraoDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Produto não encontrado"));
    }

    @Test
    @Order(3)
    void deveListarDespesas() throws Exception {
        Pagina<Despesa> paginaMock = new Pagina<>(List.of(despesaDomainPadrao), 1);

        when(listDespesaUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaMock);
        when(despesaDtoMapper.toDto(any(Despesa.class))).thenReturn(despesaPadraoDTO);

        mvc.perform(get("/api/despesas")
                        .param("offset", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dados").isArray())
                .andExpect(jsonPath("$.dados[0].valor").value(200.0))
                .andExpect(jsonPath("$.totalPaginas").value(1));
    }

    @Test
    @Order(4)
    void deveRetornar204_ListaVazia() throws Exception {
        Pagina<Despesa> paginaVazia = new Pagina<>(Collections.emptyList(), 0);

        when(listDespesaUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaVazia);

        mvc.perform(get("/api/despesas"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    void deveBuscarDespesaPorId() throws Exception {
        when(findDespesaByIdUseCase.execute(1)).thenReturn(despesaDomainPadrao);
        when(despesaDtoMapper.toDto(despesaDomainPadrao)).thenReturn(despesaPadraoDTO);

        mvc.perform(get("/api/despesas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtoId").value(1))
                .andExpect(jsonPath("$.valor").value(200.0));
    }

    @Test
    @Order(6)
    void deveRetornar404_DespesaNaoEncontrada() throws Exception {
        when(findDespesaByIdUseCase.execute(99))
                .thenThrow(new NotFoundException("Despesa não encontrada."));

        mvc.perform(get("/api/despesas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Despesa não encontrada."));
    }

    @Test
    @Order(7)
    void deveAtualizarDespesa() throws Exception {
        when(despesaDtoMapper.toDomain(any(DespesaDTO.class))).thenReturn(despesaDomainPadrao);
        when(updateDespesaUseCase.execute(eq(1), any(Despesa.class)))
                .thenReturn("Despesa atualizada com sucesso.");

        mvc.perform(put("/api/despesas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(despesaPadraoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Despesa atualizada com sucesso."));
    }

    @Test
    @Order(8)
    void deveFalharNaAtualizacao_DespesaNaoEncontrada() throws Exception {
        when(despesaDtoMapper.toDomain(any(DespesaDTO.class))).thenReturn(despesaDomainPadrao);
        when(updateDespesaUseCase.execute(eq(99), any(Despesa.class)))
                .thenThrow(new NotFoundException("Despesa não encontrada."));

        mvc.perform(put("/api/despesas/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(despesaPadraoDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Despesa não encontrada."));
    }

    @Test
    @Order(9)
    void deveDeletarDespesa() throws Exception {
        Mockito.doNothing().when(deleteDespesaUseCase).execute(1);

        mvc.perform(delete("/api/despesas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void deveFalharAoDeletar_DespesaNaoEncontrada() throws Exception {
        doThrow(new NotFoundException("Despesa não encontrada."))
                .when(deleteDespesaUseCase).execute(99);

        mvc.perform(delete("/api/despesas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Despesa não encontrada."));
    }
}
