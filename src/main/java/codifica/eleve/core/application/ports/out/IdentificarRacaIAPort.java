package codifica.eleve.core.application.ports.out;

public interface IdentificarRacaIAPort {
    String identificarRaca(byte[] imageBytes, String mimeType);
}
