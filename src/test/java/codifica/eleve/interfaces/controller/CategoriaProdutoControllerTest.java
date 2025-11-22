package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.produto.categoria.*;
import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.CategoriaProdutoDTO;
import codifica.eleve.interfaces.dtoAdapters.CategoriaProdutoDtoMapper;
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

@WebMvcTest(CategoriaProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoriaProdutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private CreateCategoriaProdutoUseCase createCategoriaProdutoUseCase;
    @MockBean
    private ListCategoriaProdutoUseCase listCategoriaProdutoUseCase;
    @MockBean
    private FindCategoriaProdutoByIdUseCase findCategoriaProdutoByIdUseCase;
    @MockBean
    private UpdateCategoriaProdutoUseCase updateCategoriaProdutoUseCase;
    @MockBean
    private DeleteCategoriaProdutoUseCase deleteCategoriaProdutoUseCase;

    @MockBean
    private CategoriaProdutoDtoMapper categoriaProdutoDtoMapper;

    private static CategoriaProdutoDTO categoriaPadraoDTO;
    private static CategoriaProduto categoriaDomainPadrao;
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
        categoriaPadraoDTO = new CategoriaProdutoDTO();
        categoriaPadraoDTO.setId(1);
        categoriaPadraoDTO.setNome("Gasto Fixo");

        categoriaDomainPadrao = new CategoriaProduto("Gasto Fixo");
        categoriaDomainPadrao.setId(new Id(1));
    }

    // ---------- POST ----------
    @Test
    @Order(1)
    void deveCadastrarCategoria() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Categoria cadastrada com sucesso.");
        resposta.put("id", 1);

        when(categoriaProdutoDtoMapper.toDomain(any(CategoriaProdutoDTO.class))).thenReturn(categoriaDomainPadrao);
        when(createCategoriaProdutoUseCase.execute(any(CategoriaProduto.class))).thenReturn(resposta);

        mvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(categoriaPadraoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Categoria cadastrada com sucesso."))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(2)
    void deveFalharNoCadastro_CategoriaDuplicada() throws Exception {
        when(categoriaProdutoDtoMapper.toDomain(any(CategoriaProdutoDTO.class))).thenReturn(categoriaDomainPadrao);
        when(createCategoriaProdutoUseCase.execute(any(CategoriaProduto.class)))
                .thenThrow(new ConflictException("Não é possível cadastrar duas categorias iguais."));

        mvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(categoriaPadraoDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Não é possível cadastrar duas categorias iguais."));
    }

    // ---------- GET ALL ----------
    @Test
    @Order(3)
    void deveListarCategorias() throws Exception {
        when(listCategoriaProdutoUseCase.execute()).thenReturn(List.of(categoriaDomainPadrao));
        when(categoriaProdutoDtoMapper.toDto(any(CategoriaProduto.class))).thenReturn(categoriaPadraoDTO);

        mvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Gasto Fixo"));
    }

    @Test
    @Order(4)
    void deveRetornar204_ListaVazia() throws Exception {
        when(listCategoriaProdutoUseCase.execute()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/categorias"))
                .andExpect(status().isNoContent());
    }

    // ---------- GET BY ID ----------
    @Test
    @Order(5)
    void deveBuscarCategoriaPorId() throws Exception {
        when(findCategoriaProdutoByIdUseCase.execute(1)).thenReturn(categoriaDomainPadrao);
        when(categoriaProdutoDtoMapper.toDto(categoriaDomainPadrao)).thenReturn(categoriaPadraoDTO);

        mvc.perform(get("/api/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Gasto Fixo"));
    }

    // ---------- PUT ----------
    @Test
    @Order(6)
    void deveAtualizarCategoria() throws Exception {
        when(categoriaProdutoDtoMapper.toDomain(any(CategoriaProdutoDTO.class))).thenReturn(categoriaDomainPadrao);
        when(updateCategoriaProdutoUseCase.execute(eq(1), any(CategoriaProduto.class)))
                .thenReturn("Categoria atualizada com sucesso.");

        mvc.perform(put("/api/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(categoriaPadraoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Categoria atualizada com sucesso."));
    }

    @Test
    @Order(7)
    void deveFalharNaAtualizacao_CategoriaDuplicada() throws Exception {
        when(categoriaProdutoDtoMapper.toDomain(any(CategoriaProdutoDTO.class))).thenReturn(categoriaDomainPadrao);
        when(updateCategoriaProdutoUseCase.execute(eq(1), any(CategoriaProduto.class)))
                .thenThrow(new ConflictException("Uma categoria com este nome já existe."));

        mvc.perform(put("/api/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(categoriaPadraoDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Uma categoria com este nome já existe."));
    }

    @Test
    @Order(8)
    void deveFalharNaAtualizacao_CategoriaInexistente() throws Exception {
        when(categoriaProdutoDtoMapper.toDomain(any(CategoriaProdutoDTO.class))).thenReturn(categoriaDomainPadrao);
        when(updateCategoriaProdutoUseCase.execute(eq(99), any(CategoriaProduto.class)))
                .thenThrow(new NotFoundException("Categoria não encontrada."));

        mvc.perform(put("/api/categorias/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(categoriaPadraoDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Categoria não encontrada."));
    }

    // ---------- DELETE ----------
    @Test
    @Order(9)
    void deveDeletarCategoria() throws Exception {
        Mockito.doNothing().when(deleteCategoriaProdutoUseCase).execute(1);

        mvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void deveFalharAoDeletar_CategoriaComProdutos() throws Exception {
        doThrow(new ConflictException("Não é possível deletar categorias que possuem produtos cadastrados."))
                .when(deleteCategoriaProdutoUseCase).execute(1);

        mvc.perform(delete("/api/categorias/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Não é possível deletar categorias que possuem produtos cadastrados."));
    }

    @Test
    @Order(11)
    void deveFalharAoDeletar_CategoriaInexistente() throws Exception {
        doThrow(new NotFoundException("Categoria não encontrada."))
                .when(deleteCategoriaProdutoUseCase).execute(99);

        mvc.perform(delete("/api/categorias/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Categoria não encontrada."));
    }
}
