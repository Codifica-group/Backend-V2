package codifica.eleve.core.domain.shared;

import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import java.util.Objects;

public class Endereco {

    private final String cep;
    private final String rua;
    private final String numero;
    private final String bairro;
    private final String cidade;
    private final String complemento;

    public Endereco(String cep, String rua, String numero, String bairro, String cidade, String complemento) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("O CEP deve conter 8 dígitos numéricos.");
        }
        if (rua == null || rua.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Rua não pode ser vazio.");
        }
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Bairro não pode ser vazio.");
        }
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo Cidade não pode ser vazio.");
        }

        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(cep, endereco.cep) && Objects.equals(rua, endereco.rua) && Objects.equals(numero, endereco.numero) && Objects.equals(bairro, endereco.bairro) && Objects.equals(cidade, endereco.cidade) && Objects.equals(complemento, endereco.complemento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, rua, numero, bairro, cidade, complemento);
    }
}
