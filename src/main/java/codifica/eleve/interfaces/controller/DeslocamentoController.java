package codifica.eleve.interfaces.controller;

import codifica.eleve.core.domain.agenda.deslocamento.Deslocamento;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.interfaces.adapters.DeslocamentoDtoMapper;
import codifica.eleve.interfaces.dto.DeslocamentoRequestDTO;
import codifica.eleve.interfaces.dto.DeslocamentoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deslocamento")
public class DeslocamentoController {

    private final codifica.eleve.core.application.usecase.deslocamento.CalcularDeslocamentoUseCase calcularDeslocamentoUseCase;
    private final DeslocamentoDtoMapper deslocamentoDtoMapper;

    public DeslocamentoController(codifica.eleve.core.application.usecase.deslocamento.CalcularDeslocamentoUseCase calcularDeslocamentoUseCase, DeslocamentoDtoMapper deslocamentoDtoMapper) {
        this.calcularDeslocamentoUseCase = calcularDeslocamentoUseCase;
        this.deslocamentoDtoMapper = deslocamentoDtoMapper;
    }

    @PostMapping("/calcular")
    public ResponseEntity<DeslocamentoResponseDTO> calcular(@RequestBody DeslocamentoRequestDTO requestDTO) {
        Endereco endereco = deslocamentoDtoMapper.toDomain(requestDTO);
        Deslocamento deslocamento = calcularDeslocamentoUseCase.execute(endereco);
        return ResponseEntity.ok(deslocamentoDtoMapper.toDto(deslocamento));
    }
}
