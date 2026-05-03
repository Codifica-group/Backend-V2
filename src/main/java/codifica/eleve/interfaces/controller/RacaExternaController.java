package codifica.eleve.interfaces.controller;

import codifica.eleve.infrastructure.adapters.RacaExternaAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/racas/externa")
public class RacaExternaController {

    private final RacaExternaAdapter racaExternaAdapter;

    public RacaExternaController(RacaExternaAdapter racaExternaAdapter) {
        this.racaExternaAdapter = racaExternaAdapter;
    }

    @GetMapping("/info/{nome}")
    public ResponseEntity<Map<String, Object>> obterInfoRaca(@PathVariable String nome) {
        String nomeDecodificado = URLDecoder.decode(nome, StandardCharsets.UTF_8);
        Map<String, Object> resposta = racaExternaAdapter.obterInfoRaca(nomeDecodificado);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/analytics/resumo")
    public ResponseEntity<Map<String, Object>> obterResumoAnalytics() {
        return ResponseEntity.ok(racaExternaAdapter.obterResumoAnalytics());
    }

    @PostMapping("/etl/sync")
    public ResponseEntity<Map<String, Object>> sincronizarRacasExternas() {
        return ResponseEntity.ok(racaExternaAdapter.sincronizarRacas());
    }
}
