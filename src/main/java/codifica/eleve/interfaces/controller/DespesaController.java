package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.despesa.*;
import codifica.eleve.core.domain.despesa.Despesa;
import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.interfaces.dto.DespesaDTO;
import codifica.eleve.interfaces.dtoAdapters.DespesaDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    private final CreateDespesaUseCase createDespesaUseCase;
    private final ListDespesaUseCase listDespesaUseCase;
    private final FindDespesaByIdUseCase findDespesaByIdUseCase;
    private final UpdateDespesaUseCase updateDespesaUseCase;
    private final DeleteDespesaUseCase deleteDespesaUseCase;
    private final DespesaDtoMapper despesaDtoMapper;

    public DespesaController(CreateDespesaUseCase createDespesaUseCase,
                             ListDespesaUseCase listDespesaUseCase,
                             FindDespesaByIdUseCase findDespesaByIdUseCase,
                             UpdateDespesaUseCase updateDespesaUseCase,
                             DeleteDespesaUseCase deleteDespesaUseCase,
                             DespesaDtoMapper despesaDtoMapper) {
        this.createDespesaUseCase = createDespesaUseCase;
        this.listDespesaUseCase = listDespesaUseCase;
        this.findDespesaByIdUseCase = findDespesaByIdUseCase;
        this.updateDespesaUseCase = updateDespesaUseCase;
        this.deleteDespesaUseCase = deleteDespesaUseCase;
        this.despesaDtoMapper = despesaDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody DespesaDTO despesaDTO) {
        Map<String, Object> response = createDespesaUseCase.execute(despesaDtoMapper.toDomain(despesaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Pagina<DespesaDTO>> listAll(@RequestParam(defaultValue = "0") int offset,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pagina<Despesa> paginaDespesa = listDespesaUseCase.execute(offset, size);
        Pagina<DespesaDTO> paginaDespesaDTO = paginaDespesa.map(despesaDtoMapper::toDto);
        return paginaDespesaDTO.dados().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(paginaDespesaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> findById(@PathVariable Integer id) {
        DespesaDTO despesa = despesaDtoMapper.toDto(findDespesaByIdUseCase.execute(id));
        return ResponseEntity.ok(despesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody DespesaDTO despesaDTO) {
        String response = updateDespesaUseCase.execute(id, despesaDtoMapper.toDomain(despesaDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteDespesaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
