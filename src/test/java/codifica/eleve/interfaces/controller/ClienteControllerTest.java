package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.cliente.*;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.core.domain.shared.Telefone;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.ClienteDTO;
import codifica.eleve.interfaces.dtoAdapters.ClienteDtoMapper;
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

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private CreateClienteUseCase createClienteUseCase;
    @MockBean
    private ListClienteUseCase listClienteUseCase;
    @MockBean
    private FindClienteByIdUseCase findClienteByIdUseCase;
    @MockBean
    private UpdateClienteUseCase updateClienteUseCase;
    @MockBean
    private DeleteClienteUseCase deleteClienteUseCase;

    @MockBean
    private ClienteDtoMapper clienteDtoMapper;

    private static ClienteDTO clienteDTOPadrao;
    private static Cliente clienteDomainPadrao;
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
        clienteDTOPadrao = new ClienteDTO();
        clienteDTOPadrao.setId(1);
        clienteDTOPadrao.setNome("Mariana Souza");
        clienteDTOPadrao.setTelefone("11912345678");
        clienteDTOPadrao.setCep("12345678");
        clienteDTOPadrao.setRua("Rua das Flores");
        clienteDTOPadrao.setNumEndereco("10");
        clienteDTOPadrao.setBairro("Centro");
        clienteDTOPadrao.setCidade("São Paulo");
        clienteDTOPadrao.setComplemento("Apto 1");

        clienteDomainPadrao = new Cliente(
                "Mariana Souza",
                new Telefone("11912345678"),
                new Endereco("12345678", "Rua das Flores", "10", "Centro", "São Paulo", "Apto 1")
        );
        clienteDomainPadrao.setId(new Id(1));
    }

    @Test
    @Order(1)
    void deveCadastrarCliente() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Cliente cadastrado com sucesso.");
        resposta.put("id", 1);

        when(clienteDtoMapper.toDomain(any(ClienteDTO.class))).thenReturn(clienteDomainPadrao);
        when(createClienteUseCase.execute(any(Cliente.class))).thenReturn(resposta);

        mvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(clienteDTOPadrao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Cliente cadastrado com sucesso."))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(2)
    void deveFalharNoCadastro_ClienteJaExiste() throws Exception {
        when(clienteDtoMapper.toDomain(any(ClienteDTO.class))).thenReturn(clienteDomainPadrao);

        when(createClienteUseCase.execute(any(Cliente.class)))
                .thenThrow(new ConflictException("Impossível cadastrar dois clientes com os mesmos dados."));

        mvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(clienteDTOPadrao)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Impossível cadastrar dois clientes com os mesmos dados."));
    }

    @Test
    @Order(3)
    void deveListarClientes() throws Exception {
        Pagina<Cliente> paginaMock = new Pagina<>(List.of(clienteDomainPadrao), 1);

        when(listClienteUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaMock);
        when(clienteDtoMapper.toDto(any(Cliente.class))).thenReturn(clienteDTOPadrao);

        mvc.perform(get("/api/clientes")
                        .param("offset", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dados").isArray())
                .andExpect(jsonPath("$.dados[0].nome").value("Mariana Souza"))
                .andExpect(jsonPath("$.totalPaginas").value(1));
    }

    @Test
    @Order(4)
    void deveRetornar204_ListaVazia() throws Exception {
        Pagina<Cliente> paginaVazia = new Pagina<>(Collections.emptyList(), 0);

        when(listClienteUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaVazia);

        mvc.perform(get("/api/clientes"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(5)
    void deveBuscarClientePorId() throws Exception {
        when(findClienteByIdUseCase.execute(1)).thenReturn(clienteDomainPadrao);
        when(clienteDtoMapper.toDto(clienteDomainPadrao)).thenReturn(clienteDTOPadrao);

        mvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Mariana Souza"));
    }

    @Test
    @Order(6)
    void deveRetornar404_ClienteNaoEncontrado() throws Exception {
        when(findClienteByIdUseCase.execute(99))
                .thenThrow(new NotFoundException("Cliente não encontrado."));

        mvc.perform(get("/api/clientes/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Cliente não encontrado."));
    }

    @Test
    @Order(7)
    void deveAtualizarCliente() throws Exception {
        when(clienteDtoMapper.toDomain(any(ClienteDTO.class))).thenReturn(clienteDomainPadrao);

        when(updateClienteUseCase.execute(eq(1), any(Cliente.class)))
                .thenReturn("Cliente atualizado com sucesso!");

        mvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(clienteDTOPadrao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente atualizado com sucesso!"));
    }

    @Test
    @Order(8)
    void deveFalharNaAtualizacao_ClienteInexistente() throws Exception {
        when(clienteDtoMapper.toDomain(any(ClienteDTO.class))).thenReturn(clienteDomainPadrao);

        when(updateClienteUseCase.execute(eq(99), any(Cliente.class)))
                .thenThrow(new NotFoundException("Cliente não encontrado."));

        mvc.perform(put("/api/clientes/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(clienteDTOPadrao)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Cliente não encontrado."));
    }

    @Test
    @Order(9)
    void deveDeletarCliente() throws Exception {
        Mockito.doNothing().when(deleteClienteUseCase).execute(1);

        mvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void deveFalharAoDeletar_ClientePossuiPets() throws Exception {
        doThrow(new ConflictException("Não é possível deletar clientes que possuem pets cadastrados."))
                .when(deleteClienteUseCase).execute(1);

        mvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Não é possível deletar clientes que possuem pets cadastrados."));
    }

    @Test
    @Order(11)
    void deveFalharAoDeletar_ClienteInexistente() throws Exception {
        doThrow(new NotFoundException("Cliente não encontrado."))
                .when(deleteClienteUseCase).execute(99);

        mvc.perform(delete("/api/clientes/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Cliente não encontrado."));
    }
}
