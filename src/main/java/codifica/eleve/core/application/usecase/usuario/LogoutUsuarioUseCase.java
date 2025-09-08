package codifica.eleve.core.application.usecase.usuario;

import codifica.eleve.core.application.ports.out.TokenPort;

public class LogoutUsuarioUseCase {

    private final TokenPort tokenPort;

    public LogoutUsuarioUseCase(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    public void execute(String token) {
        tokenPort.invalidate(token);
    }
}
