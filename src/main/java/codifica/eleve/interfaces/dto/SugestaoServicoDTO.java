package codifica.eleve.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SugestaoServicoDTO {

    private BigDecimal valor;
    private List<ServicoDTO> servicos;
    private DeslocamentoResponseDTO deslocamento;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public List<ServicoDTO> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoDTO> servicos) {
        this.servicos = servicos;
    }

    public DeslocamentoResponseDTO getDeslocamento() {
        return deslocamento;
    }

    public void setDeslocamento(DeslocamentoResponseDTO deslocamento) {
        this.deslocamento = deslocamento;
    }
}
