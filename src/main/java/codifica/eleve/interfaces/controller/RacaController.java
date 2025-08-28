package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.raca.*;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.interfaces.adapters.RacaDtoMapper;
import codifica.eleve.interfaces.dto.RacaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/racas")
public class RacaController {

    private final CreateRacaUseCase createRacaUseCase;
    private final ListRacaUseCase listRacaUseCase;
    private final FindRacaByIdUseCase findRacaByIdUseCase;
    private final UpdateRacaUseCase updateRacaUseCase;
    private final DeleteRacaUseCase deleteRacaUseCase;
    private final RacaDtoMapper racaDtoMapper;

    public RacaController(CreateRacaUseCase createRacaUseCase, ListRacaUseCase listRacaUseCase,
                          FindRacaByIdUseCase findRacaByIdUseCase, UpdateRacaUseCase updateRacaUseCase,
                          DeleteRacaUseCase deleteRacaUseCase, RacaDtoMapper racaDtoMapper) {
        this.createRacaUseCase = createRacaUseCase;
        this.listRacaUseCase = listRacaUseCase;
        this.findRacaByIdUseCase = findRacaByIdUseCase;
        this.updateRacaUseCase = updateRacaUseCase;
        this.deleteRacaUseCase = deleteRacaUseCase;
        this.racaDtoMapper = racaDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody RacaDTO racaDTO) {
        Object json = createRacaUseCase.execute(racaDtoMapper.toDomain(racaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }

    @GetMapping
    public ResponseEntity<List<RacaDTO>> list() {
        List<Raca> racas = listRacaUseCase.execute();
        List<RacaDTO> racaDTOS = racas.stream().map(racaDtoMapper::toDto).collect(Collectors.toList());
        return racaDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(racaDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RacaDTO> findById(@PathVariable Integer id) {
        Raca raca = findRacaByIdUseCase.execute(id);
        return ResponseEntity.ok(racaDtoMapper.toDto(raca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody RacaDTO racaDTO) {
        String message = updateRacaUseCase.execute(id, racaDtoMapper.toDomain(racaDTO));
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteRacaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}