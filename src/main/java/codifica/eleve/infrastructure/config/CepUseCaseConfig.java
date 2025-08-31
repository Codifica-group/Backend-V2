package codifica.eleve.infrastructure.config;

import codifica.eleve.core.application.ports.out.CepPort;
import codifica.eleve.core.application.usecase.cliente.cep.FindCepUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CepUseCaseConfig {

    @Bean
    public FindCepUseCase findCepUseCase(CepPort cepPort) {
        return new FindCepUseCase(cepPort);
    }
}
