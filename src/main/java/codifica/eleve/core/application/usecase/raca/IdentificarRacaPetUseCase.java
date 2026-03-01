package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.application.ports.out.IdentificarRacaIAPort;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

public class IdentificarRacaPetUseCase {

    private final IdentificarRacaIAPort iaPort;

    public IdentificarRacaPetUseCase(IdentificarRacaIAPort iaPort) {
        this.iaPort = iaPort;
    }

    public String execute(byte[] imageBytes, String contentType, long sizeBytes) {
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/heic"))) {
            throw new IllegalArgumentException("Formato de imagem inválido. Use JPG, PNG ou HEIC.");
        }

        if (sizeBytes > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("A imagem excede o limite de 10MB permitido para câmeras de smartphones.");
        }

        return iaPort.identificarRaca(imageBytes, contentType);
    }
}
