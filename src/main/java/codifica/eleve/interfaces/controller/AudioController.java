package codifica.eleve.interfaces.controller;

import codifica.eleve.core.application.ports.in.ProcessarAudioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    private final ProcessarAudioUseCase processarAudioUseCase;

    public AudioController(ProcessarAudioUseCase processarAudioUseCase) {
        this.processarAudioUseCase = processarAudioUseCase;
    }

    @PostMapping("/processar")
    public ResponseEntity<String> processarAudio(
            @RequestParam("audio") MultipartFile audio,
            @RequestParam(value = "prompt", required = false) String prompt) {

        try {
            if (audio.isEmpty()) {
                return ResponseEntity.badRequest().body("O arquivo de áudio não pode estar vazio.");
            }

            String mimeType = audio.getContentType();
            byte[] audioBytes = audio.getBytes();

            String respostaIA = processarAudioUseCase.execute(audioBytes, mimeType, prompt);

            return ResponseEntity.ok(respostaIA);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}
