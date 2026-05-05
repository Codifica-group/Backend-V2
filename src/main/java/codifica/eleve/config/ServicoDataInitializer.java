package codifica.eleve.config;

import codifica.eleve.infrastructure.persistence.servico.ServicoEntity;
import codifica.eleve.infrastructure.persistence.servico.ServicoJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("!dev")
public class ServicoDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ServicoDataInitializer.class);

    @Bean
    @Order(2)
    CommandLineRunner initServicos(ServicoJpaRepository servicoJpaRepository) {
        return args -> {
            List<ServicoEntity> servicosPadrao = List.of(
                    new ServicoEntity("Banho", new BigDecimal("35.00")),
                    new ServicoEntity("Tosa", new BigDecimal("50.00")),
                    new ServicoEntity("Hidatação", new BigDecimal("15.00"))
            );

            if (servicoJpaRepository.count() == 0) {
                servicoJpaRepository.saveAll(servicosPadrao);
                logger.info("SERVICOS seed inicial aplicado com {} registros padrão.", servicosPadrao.size());
                return;
            }

            List<ServicoEntity> servicosFaltantes = new ArrayList<>();
            for (ServicoEntity servico : servicosPadrao) {
                if (!servicoJpaRepository.existsByNomeIgnoreCase(servico.getNome())) {
                    servicosFaltantes.add(servico);
                }
            }

            if (servicosFaltantes.isEmpty()) {
                logger.info("SERVICOS seed ignorado porque os serviços padrão já existem.");
                return;
            }

            servicoJpaRepository.saveAll(servicosFaltantes);
            logger.info("SERVICOS seed complementar aplicado com {} registros faltantes.", servicosFaltantes.size());
        };
    }
}
