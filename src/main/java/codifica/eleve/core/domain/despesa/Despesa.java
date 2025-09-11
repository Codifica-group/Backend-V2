package codifica.eleve.core.domain.despesa;

import codifica.eleve.core.domain.produto.Produto;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

import java.time.LocalDate;

public class Despesa {
    private Id id;
    private Produto produto;
    private ValorMonetario valor;
    private LocalDate data;

    public Despesa(Produto produto, ValorMonetario valor, LocalDate data) {
        if (data == null) {
            throw new IllegalArgumentException("A data não pode ser nula.");
        }
        this.produto = produto;
        this.valor = valor;
        this.data = data;
    }

    public Despesa() {}

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ValorMonetario getValor() {
        return valor;
    }

    public void setValor(ValorMonetario valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
