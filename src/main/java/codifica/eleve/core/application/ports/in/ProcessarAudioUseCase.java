package codifica.eleve.core.application.ports.in;

public interface ProcessarAudioUseCase {
    String execute(byte[] audioBytes, String mimeType, String prompt);
}
