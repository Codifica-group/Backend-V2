package codifica.eleve.infrastructure.persistence.servico;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servico")
public class ServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private BigDecimal valorBase;

    public ServicoEntity(String nome, BigDecimal valorBase) {
        this.nome = nome;
        this.valorBase = valorBase;
    }

    public ServicoEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }
}
