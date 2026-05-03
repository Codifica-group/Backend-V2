package codifica.eleve.config;

import codifica.eleve.infrastructure.persistence.raca.porte.PorteEntity;
import codifica.eleve.infrastructure.persistence.raca.porte.PorteJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class PorteDataInitializer {

    @Bean
    @Order(1)
    CommandLineRunner initPortes(PorteJpaRepository porteJpaRepository) {
        return args -> {
            if (porteJpaRepository.count() == 0) {
                porteJpaRepository.saveAll(List.of(
                        new PorteEntity("Pequeno"),
                        new PorteEntity("Médio"),
                        new PorteEntity("Grande")
                ));
            }
        };
    }
}
