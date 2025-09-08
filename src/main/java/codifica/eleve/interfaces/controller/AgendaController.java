package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.agenda.*;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularServicoUseCase;
import codifica.eleve.interfaces.dtoAdapters.AgendaDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.FiltroDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.SugestaoServicoDtoMapper;
import codifica.eleve.interfaces.dto.AgendaDTO;
import codifica.eleve.interfaces.dto.CalcularServicoRequestDTO;
import codifica.eleve.interfaces.dto.FiltroDTO;
import codifica.eleve.interfaces.dto.ServicoDTO;
import codifica.eleve.interfaces.dto.SugestaoServicoDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final CreateAgendaUseCase createAgendaUseCase;
    private final FindAgendaByIdUseCase findAgendaByIdUseCase;
    private final ListAgendaUseCase listAgendaUseCase;
    private final UpdateAgendaUseCase updateAgendaUseCase;
    private final DeleteAgendaUseCase deleteAgendaUseCase;
    private final CalcularServicoUseCase calcularServicoUseCase;
    private final FilterAgendaUseCase filterAgendaUseCase;
    private final AgendaDtoMapper agendaDtoMapper;
    private final SugestaoServicoDtoMapper sugestaoServicoDtoMapper;
    private final FiltroDtoMapper filtroDtoMapper;

    public AgendaController(CreateAgendaUseCase createAgendaUseCase,
                            FindAgendaByIdUseCase findAgendaByIdUseCase,
                            ListAgendaUseCase listAgendaUseCase,
                            UpdateAgendaUseCase updateAgendaUseCase,
                            DeleteAgendaUseCase deleteAgendaUseCase,
                            CalcularServicoUseCase calcularServicoUseCase,
                            FilterAgendaUseCase filterAgendaUseCase,
                            AgendaDtoMapper agendaDtoMapper,
                            SugestaoServicoDtoMapper sugestaoServicoDtoMapper,
                            FiltroDtoMapper filtroDtoMapper) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.findAgendaByIdUseCase = findAgendaByIdUseCase;
        this.listAgendaUseCase = listAgendaUseCase;
        this.updateAgendaUseCase = updateAgendaUseCase;
        this.deleteAgendaUseCase = deleteAgendaUseCase;
        this.calcularServicoUseCase = calcularServicoUseCase;
        this.filterAgendaUseCase = filterAgendaUseCase;
        this.agendaDtoMapper = agendaDtoMapper;
        this.sugestaoServicoDtoMapper = sugestaoServicoDtoMapper;
        this.filtroDtoMapper = filtroDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody AgendaDTO agendaDTO) {
        Map<String, Object> response = createAgendaUseCase.execute(agendaDtoMapper.toDomain(agendaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> findById(@PathVariable Integer id) {
        AgendaDTO agenda = agendaDtoMapper.toDto(findAgendaByIdUseCase.execute(id));
        return ResponseEntity.ok(agenda);
    }

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> listAll() {
        List<AgendaDTO> agendas = listAgendaUseCase.execute().stream()
                .map(agendaDtoMapper::toDto)
                .collect(Collectors.toList());
        return agendas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(agendas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody AgendaDTO agendaDTO) {
        Map<String, Object> response = updateAgendaUseCase.execute(id, agendaDtoMapper.toDomain(agendaDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        deleteAgendaUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calcular/servico")
    public ResponseEntity<SugestaoServicoDTO> calcularServico(@RequestBody CalcularServicoRequestDTO requestDTO) {
        List<Integer> servicosId = requestDTO.getServicos().stream()
                .map(ServicoDTO::getId)
                .collect(Collectors.toList());

        var sugestao = calcularServicoUseCase.execute(requestDTO.getPetId(), servicosId);
        return ResponseEntity.ok(sugestaoServicoDtoMapper.toDto(sugestao));
    }

    @PostMapping("/filtrar")
    public ResponseEntity<List<AgendaDTO>> filtrar(@RequestBody FiltroDTO filtroDTO) {
        List<AgendaDTO> agendas = filterAgendaUseCase.execute(filtroDtoMapper.toDomain(filtroDTO)).stream()
                .map(agendaDtoMapper::toDto)
                .collect(Collectors.toList());
        return agendas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(agendas);
    }
}
