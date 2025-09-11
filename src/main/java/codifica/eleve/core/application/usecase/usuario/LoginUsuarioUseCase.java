package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.application.ports.out.*;
import codifica.eleve.core.domain.shared.exceptions.UnauthorizedException;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenPort tokenPort;
    private final LoginAttemptPort loginAttemptPort;
    private final GeolocationPort geolocationPort;
    private final RequestContextPort requestContextPort;
    private static final Logger logger = LoggerFactory.getLogger(LoginUsuarioUseCase.class);

    public LoginUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            PasswordEncoderPort passwordEncoder,
            TokenPort tokenPort,
            LoginAttemptPort loginAttemptPort,
            GeolocationPort geolocationPort,
            RequestContextPort requestContextPort) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
        this.loginAttemptPort = loginAttemptPort;
        this.geolocationPort = geolocationPort;
        this.requestContextPort = requestContextPort;
    }

    public Object execute(Usuario usuario) {
        String ip = requestContextPort.getClientIp();

        if (loginAttemptPort.isBlocked(ip)) {
            throw new UnauthorizedException("Bloqueio temporário por tentativas excessivas de login. Tente novamente mais tarde.");
        }

        Optional<Usuario> foundUserOptional = usuarioRepository.findByEmail(usuario.getEmail().getEndereco());
        if (foundUserOptional.isEmpty() || !passwordEncoder.matches(usuario.getSenha().getValor(), foundUserOptional.get().getSenhaCodificada())) {
            loginFailed(ip);
            throw new UnauthorizedException("Credenciais inválidas.");
        }

        loginSucceeded(ip);
        var response = new HashMap<String, Object>();
        response.put("mensagem", "Acesso autorizado.");
        response.put("token", tokenPort.generate(foundUserOptional.get().getEmail().getEndereco()));
        return response;
    }

    private void loginFailed(String ip) {
        loginAttemptPort.loginFailed(ip);
        if (loginAttemptPort.isBlocked(ip)) {
            String geolocation = geolocationPort.getGeolocation(ip);
            String userAgent = requestContextPort.getUserAgent();
            logger.warn("Bloqueio de IP por tentativa de login excessiva: {}/{}. IP: {}, Geolocalização: {}, User-Agent: {}, Tempo de bloqueio: {} minutos.",
                    loginAttemptPort.getAttempts(ip), 5, ip, geolocation, userAgent, loginAttemptPort.getBlockDurationMinutes());
        }
    }

    private void loginSucceeded(String ip) {
        loginAttemptPort.loginSucceeded(ip);
    }
}
