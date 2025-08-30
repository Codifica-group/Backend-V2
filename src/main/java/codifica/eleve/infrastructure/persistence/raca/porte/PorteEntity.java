package codifica.eleve.infrastructure.persistence.raca.porte;

import jakarta.persistence.*;

@Entity
@Table(name = "porte")
public class PorteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    public PorteEntity() {}

    public PorteEntity(String nome) {
        this.nome = nome;
    }

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
}
