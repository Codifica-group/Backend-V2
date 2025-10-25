package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.cliente.*;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.shared.Pagina;
import codifica.eleve.interfaces.dtoAdapters.ClienteDtoMapper;
import codifica.eleve.interfaces.dto.ClienteDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final CreateClienteUseCase createClienteUseCase;
    private final ListClienteUseCase listClienteUseCase;
    private final FindClienteByIdUseCase findClienteByIdUseCase;
    private final UpdateClienteUseCase updateClienteUseCase;
    private final DeleteClienteUseCase deleteClienteUseCase;
    private final ClienteDtoMapper clienteDtoMapper;

    public ClienteController(CreateClienteUseCase createClienteUseCase, ListClienteUseCase listClienteUseCase, FindClienteByIdUseCase findClienteByIdUseCase, UpdateClienteUseCase updateClienteUseCase, DeleteClienteUseCase deleteClienteUseCase, ClienteDtoMapper clienteDtoMapper) {
        this.createClienteUseCase = createClienteUseCase;
        this.listClienteUseCase = listClienteUseCase;
        this.findClienteByIdUseCase = findClienteByIdUseCase;
        this.updateClienteUseCase = updateClienteUseCase;
        this.deleteClienteUseCase = deleteClienteUseCase;
        this.clienteDtoMapper = clienteDtoMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid ClienteDTO clienteDTO) {
        Map<String, Object> response = createClienteUseCase.execute(clienteDtoMapper.toDomain(clienteDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Pagina<ClienteDTO>> listAll(@RequestParam(defaultValue = "0") int offset,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pagina<Cliente> paginaDeClientes = listClienteUseCase.execute(offset, size);

        List<ClienteDTO> clientesDTO = paginaDeClientes.dados().stream()
                .map(clienteDtoMapper::toDto)
                .collect(Collectors.toList());
        Pagina<ClienteDTO> response = new Pagina<>(clientesDTO, paginaDeClientes.totalPaginas());
        return clientesDTO.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        ClienteDTO cliente = clienteDtoMapper.toDto(findClienteByIdUseCase.execute(id));
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid ClienteDTO clienteDTO) {
        String response = updateClienteUseCase.execute(id, clienteDtoMapper.toDomain(clienteDTO));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteClienteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
