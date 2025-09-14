package codifica.eleve.core.application.usecase.produto;

import codifica.eleve.core.domain.despesa.DespesaRepository;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteProdutoUseCase {
    private final ProdutoRepository produtoRepository;
    private final DespesaRepository despesaRepository;

    public DeleteProdutoUseCase(ProdutoRepository produtoRepository, DespesaRepository despesaRepository) {
        this.produtoRepository = produtoRepository;
        this.despesaRepository = despesaRepository;
    }

    public void execute(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new NotFoundException("Produto não encontrado.");
        }

        if (despesaRepository.existsByProdutoId(id)) {
            throw new ConflictException("Não é possível deletar produtos que possuem despesas cadastradas.");
        }

        produtoRepository.deleteById(id);
    }
}
