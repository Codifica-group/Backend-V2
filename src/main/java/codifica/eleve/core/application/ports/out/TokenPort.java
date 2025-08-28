package codifica.eleve.core.application.ports.out;

public interface TokenPort {
    String generate(String subject);

    String validate(String token);
}