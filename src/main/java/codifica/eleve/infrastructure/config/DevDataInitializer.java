package codifica.eleve.infrastructure.config;

import codifica.eleve.infrastructure.persistence.usuario.UsuarioEntity;
import codifica.eleve.infrastructure.persistence.usuario.UsuarioJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevDataInitializer {

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            if (usuarioJpaRepository.findByEmail("admin@email.com") == null) {
                UsuarioEntity admin = new UsuarioEntity();
                admin.setNome("Admin");
                admin.setEmail("admin@email.com");
                admin.setSenha(passwordEncoder.encode("Admin#2025"));

                usuarioJpaRepository.save(admin);
            }
        };
    }
}
