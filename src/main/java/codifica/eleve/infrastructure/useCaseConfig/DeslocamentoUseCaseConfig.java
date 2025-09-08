package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.ports.out.DeslocamentoPort;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularDeslocamentoUseCase;
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
    public CalcularDeslocamentoUseCase calcularDeslocamentoUseCase(
            DeslocamentoPort deslocamentoPort,
            CalculadoraTaxa calculadoraTaxa) {
        return new CalcularDeslocamentoUseCase(deslocamentoPort, calculadoraTaxa);
    }
}
