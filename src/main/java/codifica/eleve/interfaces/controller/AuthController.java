package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.ports.out.TokenPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenPort tokenPort;

    public AuthController(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");

        String userEmail = tokenPort.validate(token);

        Map<String, String> response = Map.of(
                "status", "Valido",
                "usuário", userEmail
        );
        return ResponseEntity.ok(response);
    }
}
