package codifica.eleve.core.domain.shared;

import java.math.BigDecimal;
import java.util.Objects;

public class ValorMonetario {

    private final BigDecimal valor;

    public ValorMonetario(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor monetário não pode ser nulo ou negativo.");
        }
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValorMonetario valorMonetario = (ValorMonetario) o;
        return Objects.equals(valor, valorMonetario.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
