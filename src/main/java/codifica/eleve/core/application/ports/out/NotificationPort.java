package codifica.eleve.core.application.ports.out;

public interface NotificationPort {
    void notify(String destination, Object payload);
}
