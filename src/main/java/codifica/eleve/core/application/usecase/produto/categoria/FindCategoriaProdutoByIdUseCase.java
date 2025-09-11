package codifica.eleve.core.application.usecase.produto.categoria;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindCategoriaProdutoByIdUseCase {

    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public FindCategoriaProdutoByIdUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    public CategoriaProduto execute(Integer id) {
        return categoriaProdutoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada."));
    }
}
