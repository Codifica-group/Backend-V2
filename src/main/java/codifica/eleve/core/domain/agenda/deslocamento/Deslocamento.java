package codifica.eleve.core.domain.agenda.deslocamento;

public class Deslocamento {
    private final double distanciaKm;
    private final double tempoMinutos;
    private final double taxa;

    public Deslocamento(double distanciaKm, double tempoMinutos, double taxa) {
        this.distanciaKm = distanciaKm;
        this.tempoMinutos = tempoMinutos;
        this.taxa = taxa;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public double getTempoMinutos() {
        return tempoMinutos;
    }

    public double getTaxa() {
        return taxa;
    }
}
