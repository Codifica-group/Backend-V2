package codifica.eleve.infrastructure.useCaseConfig;

import codifica.eleve.core.application.usecase.produto.categoria.*;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoriaProdutoUseCaseConfig {

    @Bean
    public CreateCategoriaProdutoUseCase createCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        return new CreateCategoriaProdutoUseCase(categoriaProdutoRepository);
    }

    @Bean
    public FindCategoriaProdutoByIdUseCase findCategoriaProdutoByIdUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        return new FindCategoriaProdutoByIdUseCase(categoriaProdutoRepository);
    }

    @Bean
    public ListCategoriaProdutoUseCase listCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        return new ListCategoriaProdutoUseCase(categoriaProdutoRepository);
    }

    @Bean
    public UpdateCategoriaProdutoUseCase updateCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        return new UpdateCategoriaProdutoUseCase(categoriaProdutoRepository);
    }

    @Bean
    public DeleteCategoriaProdutoUseCase deleteCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository, ProdutoRepository produtoRepository) {
        return new DeleteCategoriaProdutoUseCase(categoriaProdutoRepository, produtoRepository);
    }
}
