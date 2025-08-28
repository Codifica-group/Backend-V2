package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.usecase.usuario.*;
import codifica.eleve.core.domain.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RegisterUsuarioUseCase registerUseCase;
    private final LoginUsuarioUseCase loginUseCase;
    private final ListUsuarioUseCase listUseCase;
    private final FindUsuarioByIdUseCase findByIdUseCase;
    private final UpdateUsuarioUseCase updateUseCase;
    private final DeleteUsuarioUseCase deleteUseCase;

    public UsuarioController(
            RegisterUsuarioUseCase registerUseCase,
            LoginUsuarioUseCase loginUseCase,
            ListUsuarioUseCase listUseCase,
            FindUsuarioByIdUseCase findByIdUseCase,
            UpdateUsuarioUseCase updateUseCase,
            DeleteUsuarioUseCase deleteUseCase
    ) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
        this.listUseCase = listUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody Usuario usuario) {
        String message = registerUseCase.execute(usuario);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        String token = loginUseCase.execute(usuario);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> list() {
        List<Usuario> usuarios = listUseCase.execute();
        return usuarios.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(findByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Usuario usuario) {
        String message = updateUseCase.execute(id, usuario);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        deleteUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
