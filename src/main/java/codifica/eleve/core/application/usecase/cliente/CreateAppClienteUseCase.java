package codifica.eleve.core.application.usecase.cliente;

import codifica.eleve.core.application.usecase.usuario.RegisterUsuarioUseCase;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.Senha;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.usuario.UsuarioRepository;
import codifica.eleve.interfaces.dto.ClienteDTO;
import codifica.eleve.interfaces.dto.UsuarioDTO;
import codifica.eleve.interfaces.dtoAdapters.ClienteDtoMapper;
import codifica.eleve.interfaces.dtoAdapters.UsuarioDtoMapper;

import java.util.HashMap;
import java.util.Map;

public class CreateAppClienteUseCase {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final RegisterUsuarioUseCase registerUseCase;
    private final CreateClienteUseCase createClienteUseCase;
    private final UsuarioDtoMapper usuarioMapper;
    private final ClienteDtoMapper clienteDtoMapper;

    public CreateAppClienteUseCase(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository, RegisterUsuarioUseCase registerUseCase, CreateClienteUseCase createClienteUseCase, UsuarioDtoMapper usuarioMapper, ClienteDtoMapper clienteDtoMapper) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.registerUseCase = registerUseCase;
        this.createClienteUseCase = createClienteUseCase;
        this.usuarioMapper = usuarioMapper;
        this.clienteDtoMapper = clienteDtoMapper;
    }

    public Map<String, Object> execute(ClienteDTO clienteDTO) {
        if (clienteDTO.getNome() == null || clienteDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }

        if (!Senha.isValid(clienteDTO.getSenha())) {
            throw new IllegalArgumentException("A senha é inválida. Deve ter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um número.");
        }

        if (usuarioRepository.findByEmail(clienteDTO.getEmail()).isPresent()) {
            throw new ConflictException("Impossível cadastrar dois usuários com o mesmo e-mail.");
        }

        if (clienteRepository.existsByNomeAndTelefone(clienteDTO.getNome(), clienteDTO.getTelefone())) {
            throw new ConflictException("Impossível cadastrar dois clientes com os mesmos dados.");
        }

        UsuarioDTO user = new UsuarioDTO();
        user.setNome(clienteDTO.getNome());
        user.setEmail(clienteDTO.getEmail());
        user.setSenha(clienteDTO.getSenha());
        Map<String, Object> userResponse = registerUseCase.execute(usuarioMapper.toDomain(user));
        Integer userId = Integer.parseInt(userResponse.get("id").toString());

        Cliente cliente = clienteDtoMapper.toDomain(clienteDTO);
        cliente.setUsuarioId(new Id(userId));
        Map<String, Object> clienteResponse = createClienteUseCase.execute(cliente);

        Map<String, Object> response = new HashMap<>();
        response.put("Usuario", userResponse);
        response.put("Cliente", clienteResponse);
        return response;
    }
}
