package codifica.eleve.core.application.usecase.produto.categoria;

import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteCategoriaProdutoUseCase {
    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public DeleteCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    public void execute(Integer id) {
        if (!categoriaProdutoRepository.existsById(id)) {
            throw new NotFoundException("Categoria não encontrada.");
        }

        categoriaProdutoRepository.deleteById(id);
    }
}
