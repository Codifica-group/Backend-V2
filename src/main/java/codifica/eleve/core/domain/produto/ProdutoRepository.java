package codifica.eleve.core.domain.produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    Produto save(Produto produto);
    List<Produto> saveAll(List<Produto> produtos);
    Optional<Produto> findById(Integer id);
    List<Produto> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    boolean existsByCategoriaProdutoAndNome(Integer categoriaId, String nome);
    boolean existsByCategoriaProdutoId(Integer categoriaId);
}
