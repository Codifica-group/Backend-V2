package codifica.eleve.interfaces.dto;

import codifica.eleve.interfaces.validation.SafeString;

public class DeslocamentoRequestDTO {
    private String cep;

    @SafeString
    private String rua;

    @SafeString
    private String numero;

    @SafeString
    private String bairro;

    @SafeString
    private String cidade;

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
}
