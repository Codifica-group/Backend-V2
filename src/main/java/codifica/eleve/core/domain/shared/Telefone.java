package codifica.eleve.core.domain.shared;

import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import java.util.Objects;

public class Telefone {

    private String numero;

    public Telefone(String numero) {
        if (numero == null || !numero.matches("^\\d{11}$")) {
            throw new IllegalArgumentException("Número de telefone deve ter 11 dígitos numéricos.");
        }
        this.numero = numero;
    }

    public Telefone() {}

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(numero, telefone.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}