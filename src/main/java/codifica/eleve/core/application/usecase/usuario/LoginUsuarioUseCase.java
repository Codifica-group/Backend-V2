package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.application.ports.out.PasswordEncoderPort;
import codifica.eleve.core.application.ports.out.TokenPort;
import codifica.eleve.core.application.usecase.security.GeolocationService;
import codifica.eleve.core.application.usecase.security.LoginAttemptService;
import codifica.eleve.core.domain.shared.exceptions.UnauthorizedException;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenPort;
    private final LoginAttemptService loginAttemptService;
    private final GeolocationService geolocationService;
    private static final Logger logger = LoggerFactory.getLogger(LoginUsuarioUseCase.class);

    @Value("${MAX_LOGIN_ATTEMPTS:5}")
    private int maxLoginAttempts;

    public LoginUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoder, TokenPort tokenPort, LoginAttemptService loginAttemptService, GeolocationService geolocationService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
        this.loginAttemptService = loginAttemptService;
        this.geolocationService = geolocationService;
    }

    public Object execute(Usuario usuario) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = getClientIP(request);

        if (loginAttemptService.isBlocked(ip)) {
            throw new UnauthorizedException("Bloqueio temporário por tentativas excessivas de login. Tente novamente mais tarde.");
        }

        Optional<Usuario> foundUserOptional = usuarioRepository.findByEmail(usuario.getEmail().getEndereco());
        if (foundUserOptional.isEmpty() || !passwordEncoder.matches(usuario.getSenha().getValor(), foundUserOptional.get().getSenhaCodificada())) {
            loginFailed(ip, request);
            throw new UnauthorizedException("Credenciais inválidas.");
        }

        loginSucceeded(ip);
        var response = new HashMap<String, Object>();
        response.put("mensagem", "Acesso autorizado.");
        response.put("token", tokenPort.generate(foundUserOptional.get().getEmail().getEndereco()));
        return response;
    }

    private void loginFailed(String ip, HttpServletRequest request) {
        loginAttemptService.loginFailed(ip);
        if (loginAttemptService.isBlocked(ip)) {
            String geolocation = geolocationService.getGeolocation(ip);
            logger.warn("Bloqueio de IP por tentativa de login excessiva: {}/{}. IP: {}, Geolocalização: {}, User-Agent: {}, Tempo de bloqueio: {} minutos.",
                    loginAttemptService.getAttempts(ip), maxLoginAttempts, ip, geolocation, request.getHeader("User-Agent"), loginAttemptService.getBlockDurationMinutes());
        }
    }

    private void loginSucceeded(String ip) {
        loginAttemptService.loginSucceeded(ip);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        String proxyClientIP = request.getHeader("Proxy-Client-IP");
        if (proxyClientIP != null) {
            return proxyClientIP;
        }
        String wlProxyClientIP = request.getHeader("WL-Proxy-Client-IP");
        if (wlProxyClientIP != null) {
            return wlProxyClientIP;
        }
        return request.getRemoteAddr();
    }
}
