package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.agenda.*;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularLucroUseCase;
import codifica.eleve.core.application.usecase.agenda.calculadora.CalcularServicoUseCase;
import codifica.eleve.core.domain.agenda.Agenda;
import codifica.eleve.core.domain.shared.Pagina;
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
    private final FindFutureAgendasByPetIdUseCase findFutureAgendasByPetIdUseCase;

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
                            CalcularLucroUseCase calcularLucroUseCase,
                            FindFutureAgendasByPetIdUseCase findFutureAgendasByPetIdUseCase) {
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
        this.findFutureAgendasByPetIdUseCase = findFutureAgendasByPetIdUseCase;
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
    public ResponseEntity<Pagina<AgendaDTO>> listAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pagina<Agenda> paginaAgenda = listAgendaUseCase.execute(offset, size);
        Pagina<AgendaDTO> paginaAgendaDTO = paginaAgenda.map(agendaDtoMapper::toDto);
        return paginaAgendaDTO.dados().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(paginaAgendaDTO);
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
    public ResponseEntity<Pagina<AgendaDTO>> filtrar(@RequestBody FiltroDTO filtroDTO,
                                                   @RequestParam(defaultValue = "0") int offset,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pagina<Agenda> paginaAgenda = filterAgendaUseCase.execute(filtroDtoMapper.toDomain(filtroDTO), offset, size);
        Pagina<AgendaDTO> paginaAgendaDTO = paginaAgenda.map(agendaDtoMapper::toDto);
        return paginaAgendaDTO.dados().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(paginaAgendaDTO);
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<DisponibilidadeDTO>> getDisponibilidade(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<DisponibilidadeDTO> disponibilidade = disponibilidadeAgendaUseCase.findDisponibilidade(inicio, fim);
        return disponibilidade.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(disponibilidade);
    }

    @PostMapping("/calcular/lucro")
    public ResponseEntity<LucroDTO> calcularLucro(@RequestBody LucroDTO lucroDTO) {
        Periodo periodo = new Periodo(lucroDTO.getDataInicio().atStartOfDay(), lucroDTO.getDataFim().atTime(LocalTime.MAX));
        LucroDTO lucro = calcularLucroUseCase.execute(periodo);
        return ResponseEntity.ok(lucro);
    }

    @GetMapping("/futuro/pet/{petId}")
    public ResponseEntity<AgendaDTO> findFutureByPetId(@PathVariable Integer petId) {
        return findFutureAgendasByPetIdUseCase.execute(petId)
                .map(agenda -> ResponseEntity.ok(agendaDtoMapper.toDto(agenda)))
                .orElse(ResponseEntity.noContent().build());
    }
}
