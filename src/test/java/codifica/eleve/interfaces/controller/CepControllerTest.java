package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.cliente.cep.FindCepUseCase;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.interfaces.dto.CepDTO;
import codifica.eleve.interfaces.dtoAdapters.CepDtoMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CepController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CepControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private FindCepUseCase findCepUseCase;
    @MockBean
    private CepDtoMapper cepDtoMapper;

    private static CepDTO cepPadraoDTO;
    private static Endereco enderecoPadraoDomain;

    @BeforeAll
    static void setUp() {
        cepPadraoDTO = new CepDTO();
        cepPadraoDTO.setCep("01001000");
        cepPadraoDTO.setLogradouro("Praça da Sé");
        cepPadraoDTO.setComplemento("lado ímpar");
        cepPadraoDTO.setBairro("Sé");
        cepPadraoDTO.setLocalidade("São Paulo");
        cepPadraoDTO.setUf("SP");
        cepPadraoDTO.setErro(false);

        enderecoPadraoDomain = new Endereco(
                "01001000",
                "Praça da Sé",
                null,
                "Sé",
                "São Paulo",
                "lado ímpar"
        );
    }

    @Test
    @Order(1)
    void deveBuscarCepComSucesso() throws Exception {
        when(findCepUseCase.execute("01001000")).thenReturn(enderecoPadraoDomain);
        when(cepDtoMapper.toDto(enderecoPadraoDomain)).thenReturn(cepPadraoDTO);

        mvc.perform(get("/api/cep/01001000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cep").value("01001000"))
                .andExpect(jsonPath("$.logradouro").value("Praça da Sé"))
                .andExpect(jsonPath("$.bairro").value("Sé"))
                .andExpect(jsonPath("$.localidade").value("São Paulo"))
                .andExpect(jsonPath("$.uf").value("SP"));
    }

    @Test
    @Order(2)
    void deveRetornar404_CepNaoEncontrado() throws Exception {
        when(findCepUseCase.execute("99999999"))
                .thenThrow(new NotFoundException("CEP não encontrado: 99999999"));

        mvc.perform(get("/api/cep/99999999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("CEP não encontrado: 99999999"));
    }

    @Test
    @Order(3)
    void deveRetornar400_CepInvalido() throws Exception {
        when(findCepUseCase.execute("123"))
                .thenThrow(new IllegalArgumentException("O CEP deve conter 8 dígitos numéricos."));

        mvc.perform(get("/api/cep/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("O CEP deve conter 8 dígitos numéricos."));
    }
}
