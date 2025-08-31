package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.cliente.cep.FindCepUseCase;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.interfaces.adapters.CepDtoMapper;
import codifica.eleve.interfaces.dto.CepDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cep")
public class CepController {

    private final FindCepUseCase findCepUseCase;
    private final CepDtoMapper cepDtoMapper;

    public CepController(FindCepUseCase findCepUseCase, CepDtoMapper cepDtoMapper) {
        this.findCepUseCase = findCepUseCase;
        this.cepDtoMapper = cepDtoMapper;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<CepDTO> findByCep(@PathVariable String cep) {
        Endereco endereco = findCepUseCase.execute(cep);
        return ResponseEntity.ok(cepDtoMapper.toDto(endereco));
    }
}
