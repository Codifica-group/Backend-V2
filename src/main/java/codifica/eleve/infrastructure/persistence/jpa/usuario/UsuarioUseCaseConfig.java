package codifica.eleve.infrastructure.persistence.jpa.usuario;

import codifica.eleve.application.usecase.usuario.*;
import codifica.eleve.domain.usuario.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioUseCaseConfig {

    @Bean
    public RegisterUsuarioUseCase registerUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new RegisterUsuarioUseCase(usuarioRepository);
    }

    @Bean
    public LoginUsuarioUseCase loginUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new LoginUsuarioUseCase(usuarioRepository);
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
    public UpdateUsuarioUseCase updateUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new UpdateUsuarioUseCase(usuarioRepository);
    }

    @Bean
    public DeleteUsuarioUseCase deleteUsuarioUseCase(UsuarioRepository usuarioRepository) {
        return new DeleteUsuarioUseCase(usuarioRepository);
    }
}
