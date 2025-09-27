package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.agenda.*;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularLucroUseCase;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularServicoUseCase;
import codifica.eleve.core.domain.shared.Periodo;
import codifica.eleve.interfaces.dto.*;
import codifica.eleve.interfaces.dtoAdapters.AgendaDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.FiltroDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.SugestaoServicoDtoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final DisponibilidadeAgendaUseCase disponibilidadeAgendaUseCase;
    private final AgendaDtoMapper agendaDtoMapper;
    private final SugestaoServicoDtoMapper sugestaoServicoDtoMapper;
    private final FiltroDtoMapper filtroDtoMapper;
    private final CalcularLucroUseCase calcularLucroUseCase;

    public AgendaController(CreateAgendaUseCase createAgendaUseCase,
                            FindAgendaByIdUseCase findAgendaByIdUseCase,
                            ListAgendaUseCase listAgendaUseCase,
                            UpdateAgendaUseCase updateAgendaUseCase,
                            DeleteAgendaUseCase deleteAgendaUseCase,
                            CalcularServicoUseCase calcularServicoUseCase,
                            FilterAgendaUseCase filterAgendaUseCase,
                            DisponibilidadeAgendaUseCase disponibilidadeAgendaUseCase,
                            AgendaDtoMapper agendaDtoMapper,
                            SugestaoServicoDtoMapper sugestaoServicoDtoMapper,
                            FiltroDtoMapper filtroDtoMapper,
                            CalcularLucroUseCase calcularLucroUseCase) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.findAgendaByIdUseCase = findAgendaByIdUseCase;
        this.listAgendaUseCase = listAgendaUseCase;
        this.updateAgendaUseCase = updateAgendaUseCase;
        this.deleteAgendaUseCase = deleteAgendaUseCase;
        this.calcularServicoUseCase = calcularServicoUseCase;
        this.filterAgendaUseCase = filterAgendaUseCase;
        this.disponibilidadeAgendaUseCase = disponibilidadeAgendaUseCase;
        this.agendaDtoMapper = agendaDtoMapper;
        this.sugestaoServicoDtoMapper = sugestaoServicoDtoMapper;
        this.filtroDtoMapper = filtroDtoMapper;
        this.calcularLucroUseCase = calcularLucroUseCase;
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
    public ResponseEntity<List<AgendaDTO>> listAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<AgendaDTO> agendas = listAgendaUseCase.execute(offset, size).stream()
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
    public ResponseEntity<List<AgendaDTO>> filtrar(@RequestBody FiltroDTO filtroDTO,
                                                   @RequestParam(defaultValue = "0") int offset,
                                                   @RequestParam(defaultValue = "10") int size) {
        List<AgendaDTO> agendas = filterAgendaUseCase.execute(filtroDtoMapper.toDomain(filtroDTO), offset, size).stream()
                .map(agendaDtoMapper::toDto)
                .collect(Collectors.toList());
        return agendas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(agendas);
    }

    @GetMapping("/disponibilidade/dias")
    public ResponseEntity<List<Map<String, Object>>> getDiasDisponiveis(@RequestBody Periodo periodo) {
        List<Map<String, Object>> dias = disponibilidadeAgendaUseCase.findDiasDisponiveis(periodo);
        return dias.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(dias);
    }

    @GetMapping("/disponibilidade/horarios")
    public ResponseEntity<List<LocalTime>> getHorariosDisponiveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia) {
        List<LocalTime> horarios = disponibilidadeAgendaUseCase.findHorariosDisponiveis(dia);
        return ResponseEntity.ok(horarios);
    }

    @PostMapping("/calcular/lucro")
    public ResponseEntity<LucroDTO> calcularLucro(@RequestBody LucroDTO lucroDTO) {
        Periodo periodo = new Periodo(lucroDTO.getDataInicio().atStartOfDay(), lucroDTO.getDataFim().atTime(LocalTime.MAX));
        LucroDTO lucro = calcularLucroUseCase.execute(periodo);
        return ResponseEntity.ok(lucro);
    }
}
