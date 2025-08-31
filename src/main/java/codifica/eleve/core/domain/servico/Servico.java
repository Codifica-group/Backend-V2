package codifica.eleve.core.domain.servico;

import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.ValorMonetario;

public class Servico {
    private Id id;
    private String nome;
    private ValorMonetario valor;

    public Servico(String nome, ValorMonetario valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Servico() {}

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ValorMonetario getValor() {
        return valor;
    }

    public void setValor(ValorMonetario valor) {
        this.valor = valor;
    }
}
