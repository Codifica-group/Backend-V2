package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.despesa.*;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DespesaUseCaseConfig {
    @Bean
    public CreateDespesaUseCase createDespesaUseCase(DespesaRepository despesaRepository) {
        return new CreateDespesaUseCase(despesaRepository);
    }

    @Bean
    public ListDespesaUseCase listDespesaUseCase(DespesaRepository despesaRepository) {
        return new ListDespesaUseCase(despesaRepository);
    }

    @Bean
    public FindDespesaByIdUseCase findDespesaByIdUseCase(DespesaRepository despesaRepository) {
        return new FindDespesaByIdUseCase(despesaRepository);
    }

    @Bean
    public UpdateDespesaUseCase updateDespesaUseCase(DespesaRepository despesaRepository) {
        return new UpdateDespesaUseCase(despesaRepository);
    }

    @Bean
    public DeleteDespesaUseCase deleteDespesaUseCase(DespesaRepository despesaRepository) {
        return new DeleteDespesaUseCase(despesaRepository);
    }
}
