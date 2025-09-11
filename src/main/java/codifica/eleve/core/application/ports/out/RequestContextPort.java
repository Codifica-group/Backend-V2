package codifica.eleve.core.application.ports.out;

public interface RequestContextPort {
    String getClientIp();
    String getUserAgent();
}
