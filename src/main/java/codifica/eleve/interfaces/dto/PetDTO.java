package codifica.eleve.interfaces.dto;

import codifica.eleve.interfaces.validation.SafeString;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDTO {
    private Integer id;

    @SafeString
    private String nome;

    @SafeString
    private String sexo;

    private String foto;

    private RacaDTO raca;
    private Integer racaId;
    private ClienteDTO cliente;
    private Integer clienteId;
    private PorteDTO porte;
    private Integer porteId;

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

    public RacaDTO getRaca() {
        return raca;
    }

    public void setRaca(RacaDTO raca) {
        this.raca = raca;
    }

    public Integer getRacaId() {
        return racaId;
    }

    public void setRacaId(Integer racaId) {
        this.racaId = racaId;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public PorteDTO getPorte() {
        return porte;
    }

    public void setPorte(PorteDTO porte) {
        this.porte = porte;
    }

    public Integer getPorteId() {
        return porteId;
    }

    public void setPorteId(Integer porteId) {
        this.porteId = porteId;
    }
}
