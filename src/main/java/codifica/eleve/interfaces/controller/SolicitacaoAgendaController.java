package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.solicitacao.*;
import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.core.domain.solicitacao.SolicitacaoAgenda;
import codifica.eleve.interfaces.dto.SolicitacaoAgendaDTO;
import codifica.eleve.interfaces.dtoAdapters.SolicitacaoAgendaDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/solicitacoes-agenda")
public class SolicitacaoAgendaController {

    private final CreateSolicitacaoAgendaUseCase createUseCase;
    private final FindSolicitacaoAgendaByIdUseCase findByIdUseCase;
    private final ListSolicitacaoAgendaUseCase listUseCase;
    private final UpdateSolicitacaoAgendaUseCase updateUseCase;
    private final DeleteSolicitacaoAgendaUseCase deleteUseCase;
    private final SolicitacaoAgendaDtoMapper mapper;

    public SolicitacaoAgendaController(CreateSolicitacaoAgendaUseCase createUseCase,
                                       FindSolicitacaoAgendaByIdUseCase findByIdUseCase,
                                       ListSolicitacaoAgendaUseCase listUseCase,
                                       UpdateSolicitacaoAgendaUseCase updateUseCase,
                                       DeleteSolicitacaoAgendaUseCase deleteUseCase,
                                       SolicitacaoAgendaDtoMapper mapper) {
        this.createUseCase = createUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.listUseCase = listUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody SolicitacaoAgendaDTO dto) {
        Map<String, Object> response = createUseCase.execute(mapper.toDomain(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoAgendaDTO> findById(@PathVariable Integer id) {
        SolicitacaoAgendaDTO solicitacao = mapper.toDto(findByIdUseCase.execute(id));
        return ResponseEntity.ok(solicitacao);
    }

    @GetMapping
    public ResponseEntity<Pagina<SolicitacaoAgendaDTO>> listAll(@RequestParam(defaultValue = "0") int offset,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pagina<SolicitacaoAgenda> paginaSolicitacoes = listUseCase.execute(offset, size);
        Pagina<SolicitacaoAgendaDTO> paginaSolicitacoesDTO = paginaSolicitacoes.map(mapper::toDto);
        return paginaSolicitacoesDTO.dados().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(paginaSolicitacoesDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody SolicitacaoAgendaDTO dto) {
        Map<String, Object> response = updateUseCase.execute(id, mapper.toDomain(dto));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
