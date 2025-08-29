package codifica.eleve.core.domain.shared;

import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import java.util.Objects;

public class Senha {

    private final String valor;

    public Senha(String valor) {
        if (!isValid(valor)) {
            throw new IllegalArgumentException("A senha é inválida. Deve ter no mínimo 8 caracteres, uma letra maiúscula, uma minúscula e um número.");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    private boolean isValid(String senha) {
        if (senha == null || senha.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;

        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Senha senha = (Senha) o;
        return Objects.equals(valor, senha.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}