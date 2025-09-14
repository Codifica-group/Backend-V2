package codifica.eleve.infrastructure.persistence.produto;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.produto.ProdutoRepository;
import codifica.eleve.infrastructure.adapters.ProdutoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final ProdutoJpaRepository produtoJpaRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoRepositoryImpl(ProdutoJpaRepository produtoJpaRepository, ProdutoMapper produtoMapper) {
        this.produtoJpaRepository = produtoJpaRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public Produto save(Produto produto) {
        ProdutoEntity entity = produtoMapper.toEntity(produto);
        ProdutoEntity saved = produtoJpaRepository.save(entity);
        return produtoMapper.toDomain(saved);
    }

    @Override
    public List<Produto> saveAll(List<Produto> produtos) {
        List<ProdutoEntity> entities = produtos.stream().map(produtoMapper::toEntity).collect(Collectors.toList());
        List<ProdutoEntity> saved = produtoJpaRepository.saveAll(entities);
        return saved.stream().map(produtoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        return produtoJpaRepository.findById(id).map(produtoMapper::toDomain);
    }

    @Override
    public List<Produto> findAll() {
        return produtoJpaRepository.findAll().stream().map(produtoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        produtoJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return produtoJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByCategoriaProdutoAndNome(Integer categoriaId, String nome) {
        return produtoJpaRepository.existsByCategoriaProdutoIdAndNome(categoriaId, nome);
    }

    @Override
    public boolean existsByCategoriaProdutoId(Integer categoriaId) {
        return produtoJpaRepository.existsByCategoriaProdutoId(categoriaId);
    }
}
