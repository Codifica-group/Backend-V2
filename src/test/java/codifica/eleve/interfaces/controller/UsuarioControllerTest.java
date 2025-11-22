package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.usecase.usuario.*;
import codifica.eleve.core.domain.shared.Email;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.core.domain.shared.Senha;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.interfaces.dto.UsuarioDTO;
import codifica.eleve.interfaces.dtoAdapters.UsuarioDtoMapper;
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

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private RegisterUsuarioUseCase registerUseCase;
    @MockBean
    private LoginUsuarioUseCase loginUseCase;
    @MockBean
    private LogoutUsuarioUseCase logoutUseCase;
    @MockBean
    private ListUsuarioUseCase listUseCase;
    @MockBean
    private FindUsuarioByIdUseCase findByIdUseCase;
    @MockBean
    private UpdateUsuarioUseCase updateUseCase;
    @MockBean
    private DeleteUsuarioUseCase deleteUseCase;

    @MockBean
    private UsuarioDtoMapper usuarioDtoMapper;

    private static UsuarioDTO usuarioDTOPadrao;
    private static Usuario usuarioDomainPadrao;
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
        usuarioDTOPadrao = new UsuarioDTO();
        usuarioDTOPadrao.setId(1);
        usuarioDTOPadrao.setNome("User Test");
        usuarioDTOPadrao.setEmail("test@email.com");
        usuarioDTOPadrao.setSenha("Test@1234");

        usuarioDomainPadrao = new Usuario(
                "User Test",
                new Email("test@email.com"),
                new Senha("Test@1234")
        );
        usuarioDomainPadrao.setId(new Id(1));
        usuarioDomainPadrao.setSenhaCodificada("encodedPass");
    }

    @Test
    @Order(1)
    void deveCadastrarUsuarioComSucesso() throws Exception {
        when(usuarioDtoMapper.toDomain(any(UsuarioDTO.class))).thenReturn(usuarioDomainPadrao);
        when(registerUseCase.execute(any(Usuario.class))).thenReturn("Usuário cadastrado com sucesso.");

        mvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(usuarioDTOPadrao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário cadastrado com sucesso."));
    }

    @Test
    @Order(2)
    void deveFalharAoCadastrarUsuario_EmailRepetido() throws Exception {
        when(usuarioDtoMapper.toDomain(any(UsuarioDTO.class))).thenReturn(usuarioDomainPadrao);
        when(registerUseCase.execute(any(Usuario.class)))
                .thenThrow(new ConflictException("Impossível cadastrar dois usuários com o mesmo e-mail."));

        mvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(usuarioDTOPadrao)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("CONFLICT"))
                .andExpect(jsonPath("$.message").value("Impossível cadastrar dois usuários com o mesmo e-mail."));
    }

    @Test
    @Order(3)
    void deveLogarComSucesso() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Acesso autorizado.");
        response.put("token", "fake-token");

        when(usuarioDtoMapper.toDomain(any(UsuarioDTO.class))).thenReturn(usuarioDomainPadrao);
        when(loginUseCase.execute(any(Usuario.class))).thenReturn(response);

        mvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(usuarioDTOPadrao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Acesso autorizado."))
                .andExpect(jsonPath("$.token").value("fake-token"));
    }

    @Test
    @Order(4)
    void deveListarUsuariosComConteudo() throws Exception {
        Pagina<Usuario> paginaMock = new Pagina<>(List.of(usuarioDomainPadrao), 1);

        when(listUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaMock);
        when(usuarioDtoMapper.toDto(any(Usuario.class))).thenReturn(usuarioDTOPadrao);

        mvc.perform(get("/api/usuarios")
                        .param("offset", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dados").isArray())
                .andExpect(jsonPath("$.dados[0].email").value("test@email.com"))
                .andExpect(jsonPath("$.totalPaginas").value(1));
    }

    @Test
    @Order(5)
    void deveRetornar204QuandoListaVazia() throws Exception {
        Pagina<Usuario> paginaVazia = new Pagina<>(Collections.emptyList(), 0);

        when(listUseCase.execute(any(Integer.class), any(Integer.class))).thenReturn(paginaVazia);

        mvc.perform(get("/api/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    void deveBuscarUsuarioPorId() throws Exception {
        when(findByIdUseCase.execute(1)).thenReturn(usuarioDomainPadrao);
        when(usuarioDtoMapper.toDto(usuarioDomainPadrao)).thenReturn(usuarioDTOPadrao);

        mvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("User Test"));
    }

    @Test
    @Order(7)
    void deveRetornar404_UsuarioNaoEncontrado() throws Exception {
        when(findByIdUseCase.execute(99))
                .thenThrow(new NotFoundException("Usuário não encontrado."));

        mvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Usuário não encontrado."));
    }

    @Test
    @Order(8)
    void deveAtualizarUsuario() throws Exception {
        when(usuarioDtoMapper.toDomain(any(UsuarioDTO.class))).thenReturn(usuarioDomainPadrao);
        when(updateUseCase.execute(eq(1), any(Usuario.class))).thenReturn("Usuário atualizado com sucesso.");

        mvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(usuarioDTOPadrao)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário atualizado com sucesso."));
    }

    @Test
    @Order(9)
    void deveFalharNaAtualizacao_UsuarioInexistente() throws Exception {
        when(usuarioDtoMapper.toDomain(any(UsuarioDTO.class))).thenReturn(usuarioDomainPadrao);
        when(updateUseCase.execute(eq(99), any(Usuario.class)))
                .thenThrow(new NotFoundException("Usuário não encontrado."));

        mvc.perform(put("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(usuarioDTOPadrao)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Usuário não encontrado."));
    }

    @Test
    @Order(10)
    void deveDeletarUsuario() throws Exception {
        Mockito.doNothing().when(deleteUseCase).execute(1);

        mvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(11)
    void deveFalharAoDeletarUsuarioInexistente() throws Exception {
        doThrow(new NotFoundException("Usuário não encontrado."))
                .when(deleteUseCase).execute(99);

        mvc.perform(delete("/api/usuarios/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Usuário não encontrado."));
    }
}
