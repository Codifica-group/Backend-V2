package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.raca.*;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.interfaces.dtoAdapters.RacaDtoMapper;
import codifica.eleve.interfaces.dto.RacaDTO;
import jakarta.validation.Valid;
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
    private final FindRacaByNomeUseCase findRacaByNomeUseCase;
    private final FindRacasByNomeSemelhanteUseCase findRacasByNomeSemelhanteUseCase;

    public RacaController(CreateRacaUseCase createRacaUseCase, ListRacaUseCase listRacaUseCase,
                          FindRacaByIdUseCase findRacaByIdUseCase, UpdateRacaUseCase updateRacaUseCase,
                          DeleteRacaUseCase deleteRacaUseCase, RacaDtoMapper racaDtoMapper,
                          FindRacaByNomeUseCase findRacaByNomeUseCase, FindRacasByNomeSemelhanteUseCase findRacasByNomeSemelhanteUseCase) {
        this.createRacaUseCase = createRacaUseCase;
        this.listRacaUseCase = listRacaUseCase;
        this.findRacaByIdUseCase = findRacaByIdUseCase;
        this.updateRacaUseCase = updateRacaUseCase;
        this.deleteRacaUseCase = deleteRacaUseCase;
        this.racaDtoMapper = racaDtoMapper;
        this.findRacaByNomeUseCase = findRacaByNomeUseCase;
        this.findRacasByNomeSemelhanteUseCase = findRacasByNomeSemelhanteUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid RacaDTO racaDTO) {
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

    @GetMapping("/nome/{nome}")
    public ResponseEntity<RacaDTO> findByNome(@PathVariable String nome) {
        Raca raca = findRacaByNomeUseCase.execute(nome);
        return ResponseEntity.ok(racaDtoMapper.toDto(raca));
    }

    @GetMapping("/nome/aproximado/{nome}")
    public ResponseEntity<List<RacaDTO>> findByNomeSemelhante(@PathVariable String nome) {
        List<Raca> racas = findRacasByNomeSemelhanteUseCase.execute(nome);
        List<RacaDTO> racaDTOS = racas.stream().map(racaDtoMapper::toDto).collect(Collectors.toList());
        return racaDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(racaDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid RacaDTO racaDTO) {
        String message = updateRacaUseCase.execute(id, racaDtoMapper.toDomain(racaDTO));
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteRacaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
