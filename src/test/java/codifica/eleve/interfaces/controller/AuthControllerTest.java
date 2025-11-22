package codifica.eleve.interfaces.controller;

import codifica.eleve.config.security.ApiKeyAuthFilter;
import codifica.eleve.config.security.IpBlockFilter;
import codifica.eleve.config.security.JwtAuthenticationFilter;
import codifica.eleve.core.application.ports.out.TokenPort;
import codifica.eleve.core.domain.shared.exceptions.InvalidTokenException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "endpoint.auth.validate.enabled=true")
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IpBlockFilter ipBlockFilter;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @MockBean
    private TokenPort tokenPort;

    @Test
    void deveValidarTokenComSucesso() throws Exception {
        String validToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...";
        String userEmail = "fernanda@email.com";

        when(tokenPort.validate(anyString())).thenReturn(userEmail);

        mvc.perform(post("/api/auth/validate")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Valido"))
                .andExpect(jsonPath("$.usuário").value(userEmail));
    }

    @Test
    void deveRetornar401_QuandoTokenInvalido() throws Exception {
        String invalidToken = "token-invalido";

        when(tokenPort.validate(anyString())).thenThrow(new InvalidTokenException("Token inválido ou expirado."));

        mvc.perform(post("/api/auth/validate")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value("Token inválido ou expirado."));
    }
}
