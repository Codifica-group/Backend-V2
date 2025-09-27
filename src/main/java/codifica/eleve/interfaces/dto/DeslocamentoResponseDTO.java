package codifica.eleve.interfaces.dto;

import java.math.BigDecimal;

public class DeslocamentoResponseDTO {
    private BigDecimal distanciaKm;
    private BigDecimal valor;
    private BigDecimal tempo;

    public DeslocamentoResponseDTO(BigDecimal distanciaKm, BigDecimal valor, BigDecimal tempo) {
        this.distanciaKm = distanciaKm;
        this.valor = valor;
        this.tempo = tempo;
    }

    public BigDecimal getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(BigDecimal distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTempo() {
        return tempo;
    }

    public void setTempo(BigDecimal tempo) {
        this.tempo = tempo;
    }
}
