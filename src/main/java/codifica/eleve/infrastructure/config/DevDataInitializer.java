package codifica.eleve.infrastructure.config;

import codifica.eleve.infrastructure.persistence.raca.porte.PorteEntity;
import codifica.eleve.infrastructure.persistence.raca.porte.PorteJpaRepository;
import codifica.eleve.infrastructure.persistence.usuario.UsuarioEntity;
import codifica.eleve.infrastructure.persistence.usuario.UsuarioJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Configuration
@Profile("dev")
public class DevDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DevDataInitializer.class);

    @Autowired
    private UsuarioJpaRepository usuarioJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PorteJpaRepository porteJpaRepository;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // POPULA BANCO DE DADOS
            // USUÁRIO ADMIN
            UsuarioEntity admin = new UsuarioEntity();
            admin.setNome("Admin");
            admin.setEmail("admin@email.com");
            admin.setSenha(passwordEncoder.encode("Admin#2025"));
            usuarioJpaRepository.save(admin);

            // PORTES
            porteJpaRepository.saveAll(List.of(
                    new PorteEntity("Pequeno"),
                    new PorteEntity("Médio"),
                    new PorteEntity("Grande")
            ));
        };
    }
}