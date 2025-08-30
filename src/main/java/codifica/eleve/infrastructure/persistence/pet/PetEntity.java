package codifica.eleve.infrastructure.persistence.pet;

import codifica.eleve.infrastructure.persistence.cliente.ClienteEntity;
import codifica.eleve.infrastructure.persistence.raca.RacaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "pet")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "raca_id")
    private RacaEntity raca;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

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

    public RacaEntity getRaca() {
        return raca;
    }

    public void setRaca(RacaEntity raca) {
        this.raca = raca;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }
}
