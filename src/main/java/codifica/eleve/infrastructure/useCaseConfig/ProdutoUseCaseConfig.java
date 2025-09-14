package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.produto.*;
import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProdutoUseCaseConfig {
    @Bean
    public CreateProdutoUseCase createProdutoUseCase(ProdutoRepository produtoRepository) {
        return new CreateProdutoUseCase(produtoRepository);
    }

    @Bean
    public ListProdutoUseCase listProdutoUseCase(ProdutoRepository produtoRepository) {
        return new ListProdutoUseCase(produtoRepository);
    }

    @Bean
    public FindProdutoByIdUseCase findProdutoByIdUseCase(ProdutoRepository produtoRepository) {
        return new FindProdutoByIdUseCase(produtoRepository);
    }

    @Bean
    public UpdateProdutoUseCase updateProdutoUseCase(ProdutoRepository produtoRepository) {
        return new UpdateProdutoUseCase(produtoRepository);
    }

    @Bean
    public DeleteProdutoUseCase deleteProdutoUseCase(ProdutoRepository produtoRepository, DespesaRepository despesaRepository) {
        return new DeleteProdutoUseCase(produtoRepository, despesaRepository);
    }
}
