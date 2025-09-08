package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.usuario.*;
import codifica.eleve.core.domain.usuario.Usuario;
import codifica.eleve.interfaces.dtoAdapters.UsuarioDtoMapper;
import codifica.eleve.interfaces.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RegisterUsuarioUseCase registerUseCase;
    private final LoginUsuarioUseCase loginUseCase;
    private final ListUsuarioUseCase listUseCase;
    private final FindUsuarioByIdUseCase findByIdUseCase;
    private final UpdateUsuarioUseCase updateUseCase;
    private final DeleteUsuarioUseCase deleteUseCase;
    private final UsuarioDtoMapper usuarioDtoMapper;

    public UsuarioController(
            RegisterUsuarioUseCase registerUseCase,
            LoginUsuarioUseCase loginUseCase,
            ListUsuarioUseCase listUseCase,
            FindUsuarioByIdUseCase findByIdUseCase,
            UpdateUsuarioUseCase updateUseCase,
            DeleteUsuarioUseCase deleteUseCase,
            UsuarioDtoMapper usuarioDtoMapper
    ) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
        this.listUseCase = listUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
        this.usuarioDtoMapper = usuarioDtoMapper;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UsuarioDTO usuarioDTO) {
        String message = registerUseCase.execute(usuarioDtoMapper.toDomain(usuarioDTO));
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        Object token = loginUseCase.execute(usuarioDtoMapper.toDomain(usuarioDTO));
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> list() {
        List<Usuario> usuarios = listUseCase.execute();
        List<UsuarioDTO> usuarioDTOS = usuarios.stream()
                .map(usuarioDtoMapper::toDto)
                .collect(Collectors.toList());
        return usuarioDTOS.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarioDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioDtoMapper.toDto(findByIdUseCase.execute(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        String message = updateUseCase.execute(id, usuarioDtoMapper.toDomain(usuarioDTO));
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
