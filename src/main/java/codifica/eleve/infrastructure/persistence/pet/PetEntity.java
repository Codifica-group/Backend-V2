package codifica.eleve.infrastructure.persistence.pet;

import codifica.eleve.infrastructure.persistence.cliente.ClienteEntity;
import codifica.eleve.infrastructure.persistence.raca.RacaEntity;
import codifica.eleve.infrastructure.persistence.raca.porte.PorteEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "pet")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String sexo;

    @Column(name = "foto_url", length = 2048)
    private String foto;

    @ManyToOne
    @JoinColumn(name = "raca_id")
    private RacaEntity raca;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "porte_id")
    private PorteEntity porte;

    public PetEntity(String nome, RacaEntity raca, ClienteEntity cliente) {
        this.nome = nome;
        this.raca = raca;
        this.cliente = cliente;
    }

    public PetEntity() {}

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public PorteEntity getPorte() {
        return porte;
    }

    public void setPorte(PorteEntity porte) {
        this.porte = porte;
    }
}
