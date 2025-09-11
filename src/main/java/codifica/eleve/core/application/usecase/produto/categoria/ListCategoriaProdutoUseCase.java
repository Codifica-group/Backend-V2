package codifica.eleve.core.application.usecase.produto.categoria;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;

import java.util.List;

public class ListCategoriaProdutoUseCase {
    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public ListCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    public List<CategoriaProduto> execute() {
        return categoriaProdutoRepository.findAll();
    }
}
