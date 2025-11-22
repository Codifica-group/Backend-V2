package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.produto.*;
import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.CategoriaProdutoDTO;
import codifica.eleve.interfaces.dto.ProdutoDTO;
import codifica.eleve.interfaces.dtoAdapters.ProdutoDtoMapper;
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

@WebMvcTest(ProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private CreateProdutoUseCase createProdutoUseCase;
    @MockBean
    private ListProdutoUseCase listProdutoUseCase;
    @MockBean
    private FindProdutoByIdUseCase findProdutoByIdUseCase;
    @MockBean
    private UpdateProdutoUseCase updateProdutoUseCase;
    @MockBean
    private DeleteProdutoUseCase deleteProdutoUseCase;

    @MockBean
    private ProdutoDtoMapper produtoDtoMapper;

    private static ProdutoDTO produtoPadraoDTO;
    private static Produto produtoDomainPadrao;
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
        CategoriaProdutoDTO categoriaDTO = new CategoriaProdutoDTO();
        categoriaDTO.setId(1);
        categoriaDTO.setNome("Limpeza");

        produtoPadraoDTO = new ProdutoDTO();
        produtoPadraoDTO.setId(1);
        produtoPadraoDTO.setNome("Sabão");
        produtoPadraoDTO.setCategoriaId(1);
        produtoPadraoDTO.setCategoria(categoriaDTO);

        CategoriaProduto categoriaDomain = new CategoriaProduto("Limpeza");
        categoriaDomain.setId(new Id(1));

        produtoDomainPadrao = new Produto("Sabão", categoriaDomain);
        produtoDomainPadrao.setId(new Id(1));
    }

    @Test
    @Order(1)
    void deveCadastrarProduto() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Produto cadastrado com sucesso.");
        resposta.put("id", 1);

        when(produtoDtoMapper.toDomain(any(ProdutoDTO.class))).thenReturn(produtoDomainPadrao);
        when(createProdutoUseCase.execute(any(Produto.class))).thenReturn(resposta);

        mvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(produtoPadraoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Produto cadastrado com sucesso."))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(2)
    void deveFalharNoCadastro_ProdutoDuplicado() throws Exception {
        when(produtoDtoMapper.toDomain(any(ProdutoDTO.class))).thenReturn(produtoDomainPadrao);
        when(createProdutoUseCase.execute(any(Produto.class)))
                .thenThrow(new ConflictException("Não é possível cadastrar dois produtos iguais na mesma categoria."));

        mvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(produtoPadraoDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Não é possível cadastrar dois produtos iguais na mesma categoria."));
    }

    @Test
    @Order(3)
    void deveListarProdutos() throws Exception {
        when(listProdutoUseCase.execute()).thenReturn(List.of(produtoDomainPadrao));
        when(produtoDtoMapper.toDto(any(Produto.class))).thenReturn(produtoPadraoDTO);

        mvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Sabão"));
    }

    @Test
    @Order(4)
    void deveRetornar204_ListaVazia() throws Exception {
        when(listProdutoUseCase.execute()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/produtos"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    void deveBuscarProdutoPorId() throws Exception {
        when(findProdutoByIdUseCase.execute(1)).thenReturn(produtoDomainPadrao);
        when(produtoDtoMapper.toDto(produtoDomainPadrao)).thenReturn(produtoPadraoDTO);

        mvc.perform(get("/api/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Sabão"));
    }

    @Test
    @Order(6)
    void deveAtualizarProduto() throws Exception {
        when(produtoDtoMapper.toDomain(any(ProdutoDTO.class))).thenReturn(produtoDomainPadrao);
        when(updateProdutoUseCase.execute(eq(1), any(Produto.class))).thenReturn("Produto atualizado com sucesso.");

        mvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(produtoPadraoDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto atualizado com sucesso."));
    }

    @Test
    @Order(7)
    void deveFalharNaAtualizacao_ProdutoInexistente() throws Exception {
        when(produtoDtoMapper.toDomain(any(ProdutoDTO.class))).thenReturn(produtoDomainPadrao);
        when(updateProdutoUseCase.execute(eq(99), any(Produto.class)))
                .thenThrow(new NotFoundException("Produto não encontrado."));

        mvc.perform(put("/api/produtos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(produtoPadraoDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Produto não encontrado."));
    }

    @Test
    @Order(8)
    void deveDeletarProduto() throws Exception {
        Mockito.doNothing().when(deleteProdutoUseCase).execute(1);

        mvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(9)
    void deveFalharAoDeletar_ProdutoInexistente() throws Exception {
        doThrow(new NotFoundException("Produto não encontrado."))
                .when(deleteProdutoUseCase).execute(99);

        mvc.perform(delete("/api/produtos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Produto não encontrado."));
    }

    @Test
    @Order(10)
    void deveFalharAoDeletar_ProdutoComDespesas() throws Exception {
        doThrow(new ConflictException("Não é possível deletar produtos que possuem despesas cadastradas."))
                .when(deleteProdutoUseCase).execute(1);

        mvc.perform(delete("/api/produtos/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Não é possível deletar produtos que possuem despesas cadastradas."));
    }
}
