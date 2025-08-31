package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.servico.*;
import codifica.eleve.interfaces.adapters.ServicoDtoMapper;
import codifica.eleve.interfaces.dto.ServicoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final CreateServicoUseCase createServicoUseCase;
    private final ListServicoUseCase listServicoUseCase;
    private final UpdateServicoUseCase updateServicoUseCase;
    private final DeleteServicoUseCase deleteServicoUseCase;
    private final FindServicoByIdUseCase findServicoByIdUseCase;
    private final ServicoDtoMapper servicoDtoMapper;

    public ServicoController(CreateServicoUseCase createServicoUseCase,
                             ListServicoUseCase listServicoUseCase,
                             UpdateServicoUseCase updateServicoUseCase,
                             DeleteServicoUseCase deleteServicoUseCase,
                             FindServicoByIdUseCase findServicoByIdUseCase,
                             ServicoDtoMapper servicoDtoMapper) {
        this.createServicoUseCase = createServicoUseCase;
        this.listServicoUseCase = listServicoUseCase;
        this.updateServicoUseCase = updateServicoUseCase;
        this.deleteServicoUseCase = deleteServicoUseCase;
        this.findServicoByIdUseCase = findServicoByIdUseCase;
        this.servicoDtoMapper = servicoDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody ServicoDTO servicoDTO) {
        Map<String, Object> response = createServicoUseCase.execute(servicoDtoMapper.toDomain(servicoDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listAll() {
        List<ServicoDTO> servicos = listServicoUseCase.execute().stream()
                .map(servicoDtoMapper::toDto)
                .collect(Collectors.toList());
        return servicos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> findById(@PathVariable Integer id) {
        ServicoDTO servico = servicoDtoMapper.toDto(findServicoByIdUseCase.execute(id));
        return ResponseEntity.ok(servico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody ServicoDTO servicoDTO) {
        String response = updateServicoUseCase.execute(id, servicoDtoMapper.toDomain(servicoDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteServicoUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
