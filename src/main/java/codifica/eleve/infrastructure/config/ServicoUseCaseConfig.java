package codifica.eleve.infrastructure.config;

import codifica.eleve.core.application.usecase.servico.*;
import codifica.eleve.core.domain.servico.ServicoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicoUseCaseConfig {
    @Bean
    public CreateServicoUseCase createServicoUseCase(ServicoRepository servicoRepository) {
        return new CreateServicoUseCase(servicoRepository);
    }

    @Bean
    public ListServicoUseCase listServicoUseCase(ServicoRepository servicoRepository) {
        return new ListServicoUseCase(servicoRepository);
    }

    @Bean
    public UpdateServicoUseCase updateServicoUseCase(ServicoRepository servicoRepository) {
        return new UpdateServicoUseCase(servicoRepository);
    }

    @Bean
    public DeleteServicoUseCase deleteServicoUseCase(ServicoRepository servicoRepository) {
        return new DeleteServicoUseCase(servicoRepository);
    }

    @Bean
    public FindServicoByIdUseCase findServicoByIdUseCase(ServicoRepository servicoRepository) {
        return new FindServicoByIdUseCase(servicoRepository);
    }
}
