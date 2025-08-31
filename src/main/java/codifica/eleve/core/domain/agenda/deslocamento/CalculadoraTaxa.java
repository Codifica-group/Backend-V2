package codifica.eleve.core.domain.agenda.deslocamento;

public class CalculadoraTaxa {

    public double calcular(ParametrosCalculo params) {
        double custoCombustivel = (params.distanciaKm() / params.kmPorLitro()) * params.precoGasolina();

        double margemLucro = params.margemLucroBase();
        if (params.distanciaKm() >= params.distanciaLimiteIntermediaria() && params.distanciaKm() < params.distanciaLimiteAlta()) {
            margemLucro = params.margemLucroIntermediaria();
        } else if (params.distanciaKm() >= params.distanciaLimiteAlta()) {
            margemLucro = params.margemLucroAlta();
        }

        return (custoCombustivel * margemLucro) * 2;
    }

    public record ParametrosCalculo(
            double distanciaKm,
            double precoGasolina,
            double kmPorLitro,
            double margemLucroBase,
            double margemLucroIntermediaria,
            double margemLucroAlta,
            double distanciaLimiteIntermediaria,
            double distanciaLimiteAlta
    ) {}
}
