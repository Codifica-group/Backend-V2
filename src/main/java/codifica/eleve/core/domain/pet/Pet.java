package codifica.eleve.core.domain.pet;

import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;

public class Pet {

    private Id id;
    private String nome;
    private Raca raca;
    private Cliente cliente;

    public Pet(String nome, Raca raca, Cliente cliente) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do pet não pode ser vazio.");
        }
        this.nome = nome;
        this.raca = raca;
        this.cliente = cliente;
    }

    public Pet() {}

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

    public Raca getRaca() {
        return raca;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}