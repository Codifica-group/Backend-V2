package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.produto.*;
import codifica.eleve.interfaces.dto.ProdutoDTO;
import codifica.eleve.interfaces.dtoAdapters.ProdutoDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final CreateProdutoUseCase createProdutoUseCase;
    private final ListProdutoUseCase listProdutoUseCase;
    private final FindProdutoByIdUseCase findProdutoByIdUseCase;
    private final UpdateProdutoUseCase updateProdutoUseCase;
    private final DeleteProdutoUseCase deleteProdutoUseCase;
    private final ProdutoDtoMapper produtoDtoMapper;

    public ProdutoController(CreateProdutoUseCase createProdutoUseCase,
                             ListProdutoUseCase listProdutoUseCase,
                             FindProdutoByIdUseCase findProdutoByIdUseCase,
                             UpdateProdutoUseCase updateProdutoUseCase,
                             DeleteProdutoUseCase deleteProdutoUseCase,
                             ProdutoDtoMapper produtoDtoMapper) {
        this.createProdutoUseCase = createProdutoUseCase;
        this.listProdutoUseCase = listProdutoUseCase;
        this.findProdutoByIdUseCase = findProdutoByIdUseCase;
        this.updateProdutoUseCase = updateProdutoUseCase;
        this.deleteProdutoUseCase = deleteProdutoUseCase;
        this.produtoDtoMapper = produtoDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid ProdutoDTO produtoDTO) {
        Map<String, Object> response = createProdutoUseCase.execute(produtoDtoMapper.toDomain(produtoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listAll() {
        List<ProdutoDTO> produtos = listProdutoUseCase.execute().stream()
                .map(produtoDtoMapper::toDto)
                .collect(Collectors.toList());
        return produtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Integer id) {
        ProdutoDTO produto = produtoDtoMapper.toDto(findProdutoByIdUseCase.execute(id));
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid ProdutoDTO produtoDTO) {
        String response = updateProdutoUseCase.execute(id, produtoDtoMapper.toDomain(produtoDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteProdutoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
