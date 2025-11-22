package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.pet.*;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.porte.Porte;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.core.domain.shared.Telefone;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.ClienteDTO;
import codifica.eleve.interfaces.dto.PetDTO;
import codifica.eleve.interfaces.dto.RacaDTO;
import codifica.eleve.interfaces.dtoAdapters.PetDtoMapper;
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

@WebMvcTest(PetController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private CreatePetUseCase createPetUseCase;
    @MockBean
    private ListPetUseCase listPetUseCase;
    @MockBean
    private FindPetByIdUseCase findPetByIdUseCase;
    @MockBean
    private FindPetsByClienteIdUseCase findPetsByClienteIdUseCase;
    @MockBean
    private UpdatePetUseCase updatePetUseCase;
    @MockBean
    private DeletePetUseCase deletePetUseCase;

    @MockBean
    private PetDtoMapper petDtoMapper;

    private static PetDTO petDTOPadrao;
    private static Pet petDomainPadrao;
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
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1);
        clienteDTO.setNome("Mariana Souza");

        RacaDTO racaDTO = new RacaDTO();
        racaDTO.setId(1);
        racaDTO.setNome("Golden Retriever");

        petDTOPadrao = new PetDTO();
        petDTOPadrao.setId(1);
        petDTOPadrao.setNome("Thor");
        petDTOPadrao.setRacaId(1);
        petDTOPadrao.setRaca(racaDTO);
        petDTOPadrao.setClienteId(1);
        petDTOPadrao.setCliente(clienteDTO);

        Porte porte = new Porte();
        porte.setId(new Id(1));
        porte.setNome("Grande");

        Raca raca = new Raca("Golden Retriever", porte);
        raca.setId(new Id(1));

        Cliente cliente = new Cliente(
                "Mariana Souza",
                new Telefone("11912345678"),
                new Endereco("12345678", "Rua Test", "10", "Bairro", "Cidade", "")
        );
        cliente.setId(new Id(1));

        petDomainPadrao = new Pet("Thor", raca, cliente);
        petDomainPadrao.setId(new Id(1));
    }

    // ---------- POST (Create) ----------
    @Test
    @Order(1)
    void deveCadastrarPet() throws Exception {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Pet cadastrado com sucesso.");
        resposta.put("id", 1);

        when(petDtoMapper.toDomain(any(PetDTO.class))).thenReturn(petDomainPadrao);

        when(createPetUseCase.execute(any(Pet.class))).thenReturn(resposta);

        mvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(petDTOPadrao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem").value("Pet cadastrado com sucesso."))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Order(2)
    void deveFalharCadastro_PetJaExiste() throws Exception {
        when(petDtoMapper.toDomain(any(PetDTO.class))).thenReturn(petDomainPadrao);

        when(createPetUseCase.execute(any(Pet.class)))
                .thenThrow(new ConflictException("Impossível cadastrar dois pets com dados iguais."));

        mvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(petDTOPadrao)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Impossível cadastrar dois pets com dados iguais."));
    }

    // ---------- GET ALL (List) ----------
    @Test
    @Order(3)
    void deveListarPets() throws Exception {
        Pagina<Pet> paginaMock = new Pagina<>(List.of(petDomainPadrao), 1);

        when(listPetUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaMock);

        when(petDtoMapper.toDto(any(Pet.class))).thenReturn(petDTOPadrao);

        mvc.perform(get("/api/pets")
                        .param("offset", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dados").isArray())
                .andExpect(jsonPath("$.dados[0].nome").value("Thor"))
                .andExpect(jsonPath("$.dados[0].raca.nome").value("Golden Retriever"))
                .andExpect(jsonPath("$.totalPaginas").value(1));
    }

    @Test
    @Order(4)
    void deveRetornar204_ListaVazia() throws Exception {
        Pagina<Pet> paginaVazia = new Pagina<>(Collections.emptyList(), 0);

        when(listPetUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaVazia);

        mvc.perform(get("/api/pets"))
                .andExpect(status().isNoContent());
    }

    // ---------- GET by ID ----------
    @Test
    @Order(5)
    void deveBuscarPetPorId() throws Exception {
        when(findPetByIdUseCase.execute(1)).thenReturn(petDomainPadrao);
        when(petDtoMapper.toDto(petDomainPadrao)).thenReturn(petDTOPadrao);

        mvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Thor"))
                .andExpect(jsonPath("$.cliente.nome").value("Mariana Souza"));
    }

    @Test
    @Order(6)
    void deveRetornar404_PetNaoEncontrado() throws Exception {
        when(findPetByIdUseCase.execute(99))
                .thenThrow(new NotFoundException("Pet não encontrado."));

        mvc.perform(get("/api/pets/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Pet não encontrado."));
    }

    // ---------- PUT (Update) ----------
    @Test
    @Order(7)
    void deveAtualizarPet() throws Exception {
        when(petDtoMapper.toDomain(any(PetDTO.class))).thenReturn(petDomainPadrao);

        when(updatePetUseCase.execute(eq(1), any(Pet.class)))
                .thenReturn("Pet atualizado com sucesso.");

        mvc.perform(put("/api/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(petDTOPadrao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Pet atualizado com sucesso."));
    }

    @Test
    @Order(8)
    void deveFalharAtualizacao_PetInexistente() throws Exception {
        when(petDtoMapper.toDomain(any(PetDTO.class))).thenReturn(petDomainPadrao);

        when(updatePetUseCase.execute(eq(99), any(Pet.class)))
                .thenThrow(new NotFoundException("Pet não encontrado."));

        mvc.perform(put("/api/pets/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(petDTOPadrao)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Pet não encontrado."));
    }

    // ---------- DELETE ----------
    @Test
    @Order(9)
    void deveDeletarPet() throws Exception {
        Mockito.doNothing().when(deletePetUseCase).execute(1);

        mvc.perform(delete("/api/pets/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void deveFalharAoDeletar_PetInexistente() throws Exception {
        doThrow(new NotFoundException("Pet não encontrado."))
                .when(deletePetUseCase).execute(99);

        mvc.perform(delete("/api/pets/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Pet não encontrado."));
    }
}
