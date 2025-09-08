package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.ports.out.PasswordEncoderPort;
import codifica.eleve.core.application.ports.out.TokenPort;
import codifica.eleve.core.application.usecase.security.GeolocationService;
import codifica.eleve.core.application.usecase.security.LoginAttemptService;
import codifica.eleve.core.application.usecase.usuario.*;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioUseCaseConfig {

    @Bean
    public RegisterUsuarioUseCase registerUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoderPort) {
        return new RegisterUsuarioUseCase(usuarioRepository, passwordEncoderPort);
    }

    @Bean
    public LoginUsuarioUseCase loginUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoder, TokenPort tokenPort, LoginAttemptService loginAttemptService, GeolocationService geolocationService) {
        return new LoginUsuarioUseCase(usuarioRepository, passwordEncoder, tokenPort, loginAttemptService, geolocationService);
    }

    @Bean
    public LogoutUsuarioUseCase logoutUsuarioUseCase(TokenPort tokenPort) {
        return new LogoutUsuarioUseCase(tokenPort);
    }

    @Bean
    public ListUsuarioUseCase listUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new ListUsuarioUseCase(usuarioRepository);
    }

    @Bean
    public FindUsuarioByIdUseCase findUsuarioByIdUseCase(UsuarioRepository usuarioRepository) {
        return new FindUsuarioByIdUseCase(usuarioRepository);
    }

    @Bean
    public UpdateUsuarioUseCase updateUsuarioUseCase(UsuarioRepository usuarioRepository, PasswordEncoderPort passwordEncoderPort) {
        return new UpdateUsuarioUseCase(usuarioRepository, passwordEncoderPort);
    }

    @Bean
    public DeleteUsuarioUseCase deleteUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new DeleteUsuarioUseCase(usuarioRepository);
    }

    @Bean
    public LoginAttemptService loginAttemptService() {
        return new LoginAttemptService();
    }

    @Bean
    public GeolocationService geolocationService() {
        return new GeolocationService();
    }
}
