package codifica.eleve.infrastructure.config;

import codifica.eleve.core.application.ports.out.DeslocamentoPort;
import codifica.eleve.core.domain.agenda.deslocamento.CalculadoraTaxa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeslocamentoUseCaseConfig {

    @Bean
    public CalculadoraTaxa calculadoraTaxa() {
        return new CalculadoraTaxa();
    }

    @Bean
    public codifica.eleve.core.application.usecase.deslocamento.CalcularDeslocamentoUseCase calcularDeslocamentoUseCase(
            DeslocamentoPort deslocamentoPort,
            CalculadoraTaxa calculadoraTaxa) {
        return new codifica.eleve.core.application.usecase.deslocamento.CalcularDeslocamentoUseCase(deslocamentoPort, calculadoraTaxa);
    }
}
