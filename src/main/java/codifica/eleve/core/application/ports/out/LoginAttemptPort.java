package codifica.eleve.core.application.ports.out;

public interface LoginAttemptPort {
    void loginSucceeded(String key);
    void loginFailed(String key);
    boolean isBlocked(String key);
    int getAttempts(String key);
    int getBlockDurationMinutes();
}
