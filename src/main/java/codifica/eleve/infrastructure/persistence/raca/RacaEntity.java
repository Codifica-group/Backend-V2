package codifica.eleve.infrastructure.persistence.raca;

import codifica.eleve.infrastructure.persistence.raca.porte.PorteEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "raca")
public class RacaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "porte_id", nullable = false)
    private PorteEntity porte;

    public RacaEntity(String nome, PorteEntity porte) {
        this.nome = nome;
        this.porte = porte;
    }

    public RacaEntity() {}

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

    public PorteEntity getPorte() {
        return porte;
    }

    public void setPorte(PorteEntity porte) {
        this.porte = porte;
    }
}
