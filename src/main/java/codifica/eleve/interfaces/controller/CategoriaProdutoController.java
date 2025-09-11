package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.produto.categoria.*;
import codifica.eleve.interfaces.dto.CategoriaProdutoDTO;
import codifica.eleve.interfaces.dtoAdapters.CategoriaProdutoDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaProdutoController {

    private final CreateCategoriaProdutoUseCase createCategoriaProdutoUseCase;
    private final FindCategoriaProdutoByIdUseCase findCategoriaProdutoByIdUseCase;
    private final ListCategoriaProdutoUseCase listCategoriaProdutoUseCase;
    private final UpdateCategoriaProdutoUseCase updateCategoriaProdutoUseCase;
    private final DeleteCategoriaProdutoUseCase deleteCategoriaProdutoUseCase;
    private final CategoriaProdutoDtoMapper categoriaProdutoDtoMapper;

    public CategoriaProdutoController(CreateCategoriaProdutoUseCase createCategoriaProdutoUseCase,
                                      ListCategoriaProdutoUseCase listCategoriaProdutoUseCase,
                                      FindCategoriaProdutoByIdUseCase findCategoriaProdutoByIdUseCase,
                                      UpdateCategoriaProdutoUseCase updateCategoriaProdutoUseCase,
                                      DeleteCategoriaProdutoUseCase deleteCategoriaProdutoUseCase,
                                      CategoriaProdutoDtoMapper categoriaProdutoDtoMapper) {
        this.createCategoriaProdutoUseCase = createCategoriaProdutoUseCase;
        this.listCategoriaProdutoUseCase = listCategoriaProdutoUseCase;
        this.findCategoriaProdutoByIdUseCase = findCategoriaProdutoByIdUseCase;
        this.updateCategoriaProdutoUseCase = updateCategoriaProdutoUseCase;
        this.deleteCategoriaProdutoUseCase = deleteCategoriaProdutoUseCase;
        this.categoriaProdutoDtoMapper = categoriaProdutoDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid CategoriaProdutoDTO categoriaProdutoDTO) {
        Map<String, Object> response = createCategoriaProdutoUseCase.execute(categoriaProdutoDtoMapper.toDomain(categoriaProdutoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoDTO> findById(@PathVariable Integer id) {
        CategoriaProdutoDTO categoria = categoriaProdutoDtoMapper.toDto(findCategoriaProdutoByIdUseCase.execute(id));
        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProdutoDTO>> listAll() {
        List<CategoriaProdutoDTO> categorias = listCategoriaProdutoUseCase.execute().stream()
                .map(categoriaProdutoDtoMapper::toDto)
                .collect(Collectors.toList());
        return categorias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid CategoriaProdutoDTO categoriaProdutoDTO) {
        String response = updateCategoriaProdutoUseCase.execute(id, categoriaProdutoDtoMapper.toDomain(categoriaProdutoDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteCategoriaProdutoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
