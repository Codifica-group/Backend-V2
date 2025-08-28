package codifica.eleve.core.domain.shared.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message) {
        super(message);
    }
}
