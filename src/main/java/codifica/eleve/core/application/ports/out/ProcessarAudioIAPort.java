package codifica.eleve.core.application.ports.out;

public interface ProcessarAudioIAPort {
    String processarAudio(byte[] audioBytes, String mimeType, String prompt);
}
