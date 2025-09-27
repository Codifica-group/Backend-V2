package codifica.eleve.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LucroDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate dataInicio;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDate dataFim;

    private BigDecimal total;
    private BigDecimal entrada;
    private BigDecimal saida;

    public LucroDTO(BigDecimal total, BigDecimal entrada, BigDecimal saida) {
        this.total = total;
        this.entrada = entrada;
        this.saida = saida;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getEntrada() {
        return entrada;
    }

    public void setEntrada(BigDecimal entrada) {
        this.entrada = entrada;
    }

    public BigDecimal getSaida() {
        return saida;
    }

    public void setSaida(BigDecimal saida) {
        this.saida = saida;
    }
}
