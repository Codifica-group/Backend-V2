package codifica.eleve.core.application.usecase.produto.categoria;

import codifica.eleve.core.domain.produto.categoria.CategoriaProduto;
import codifica.eleve.core.domain.produto.categoria.CategoriaProdutoRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateCategoriaProdutoUseCase {
    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public UpdateCategoriaProdutoUseCase(CategoriaProdutoRepository categoriaProdutoRepository) {
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    public String execute(Integer id, CategoriaProduto categoriaProduto) {
        if (!categoriaProdutoRepository.existsById(id)) {
            throw new NotFoundException("Categoria não encontrada.");
        }
        if (categoriaProdutoRepository.existsByNome(categoriaProduto.getNome())) {
            throw new ConflictException("Já existe uma categoria com o mesmo nome.");
        }

        categoriaProduto.setId(new Id(id));
        categoriaProdutoRepository.save(categoriaProduto);
        return "Categoria atualizada com sucesso.";
    }
}
