package codifica.eleve.interfaces.dto;

import java.math.BigDecimal;

public class DeslocamentoResponseDTO {
    private BigDecimal distanciaKm;
    private BigDecimal taxa;
    private BigDecimal tempoMinutos;

    public DeslocamentoResponseDTO(BigDecimal distanciaKm, BigDecimal taxa, BigDecimal tempoMinutos) {
        this.distanciaKm = distanciaKm;
        this.taxa = taxa;
        this.tempoMinutos = tempoMinutos;
    }

    public BigDecimal getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(BigDecimal distanciaKm) { this.distanciaKm = distanciaKm; }
    public BigDecimal getTaxa() { return taxa; }
    public void setTaxa(BigDecimal taxa) { this.taxa = taxa; }
    public BigDecimal getTempoMinutos() { return tempoMinutos; }
    public void setTempoMinutos(BigDecimal tempoMinutos) { this.tempoMinutos = tempoMinutos; }
}
