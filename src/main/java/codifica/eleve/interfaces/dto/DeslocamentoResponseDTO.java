package codifica.eleve.interfaces.dto;

public class DeslocamentoResponseDTO {
    private double distanciaKm;
    private double taxa;
    private double tempoMinutos;

    public DeslocamentoResponseDTO(double distanciaKm, double taxa, double tempoMinutos) {
        this.distanciaKm = distanciaKm;
        this.taxa = taxa;
        this.tempoMinutos = tempoMinutos;
    }

    public double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }
    public double getTaxa() { return taxa; }
    public void setTaxa(double taxa) { this.taxa = taxa; }
    public double getTempoMinutos() { return tempoMinutos; }
    public void setTempoMinutos(double tempoMinutos) { this.tempoMinutos = tempoMinutos; }
}
