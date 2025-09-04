package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.agenda.CreateAgendaUseCase;
import codifica.eleve.interfaces.adapters.AgendaDtoMapper;
import codifica.eleve.interfaces.dto.AgendaDTO;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final CreateAgendaUseCase createAgendaUseCase;
    private final AgendaDtoMapper agendaDtoMapper;

    public AgendaController(CreateAgendaUseCase createAgendaUseCase, AgendaDtoMapper agendaDtoMapper) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.agendaDtoMapper = agendaDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody AgendaDTO agendaDTO) {
        Map<String, Object> response = createAgendaUseCase.execute(agendaDtoMapper.toDomain(agendaDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
