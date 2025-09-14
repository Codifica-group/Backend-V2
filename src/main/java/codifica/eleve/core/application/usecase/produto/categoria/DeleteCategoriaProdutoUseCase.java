package codifica.eleve.core.application.usecase.produto.categoria;

import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteCategoriaProdutoUseCase {
    private final CategoriaProdutoRepository categoriaProdutoRepository;
    private final ProdutoRepository produtoRepository;

    public DeleteCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository, ProdutoRepository produtoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
        this.produtoRepository = produtoRepository;
    }

    public void execute(Integer id) {
        if (!categoriaProdutoRepository.existsById(id)) {
            throw new NotFoundException("Categoria não encontrada.");
        }

        if (produtoRepository.existsByCategoriaProdutoId(id)) {
            throw new ConflictException("Não é possível deletar categorias que possuem produtos cadastrados.");
        }

        categoriaProdutoRepository.deleteById(id);
    }
}
