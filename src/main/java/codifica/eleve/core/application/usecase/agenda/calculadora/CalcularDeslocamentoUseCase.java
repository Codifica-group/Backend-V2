package codifica.eleve.core.application.usecase.agenda.calculadora;

import codifica.eleve.core.application.ports.out.DeslocamentoPort;
import codifica.eleve.core.domain.agenda.deslocamento.CalculadoraTaxa;
import codifica.eleve.core.domain.agenda.deslocamento.Deslocamento;
import codifica.eleve.core.domain.agenda.deslocamento.DistanciaETempo;
import codifica.eleve.core.domain.shared.Endereco;
import org.springframework.beans.factory.annotation.Value;

public class CalcularDeslocamentoUseCase {

    private final DeslocamentoPort deslocamentoPort;
    private final CalculadoraTaxa calculadoraTaxa;

    @Value("${taxa.preco.gasolina}")
    private double precoGasolina;
    @Value("${taxa.km.por.litro}")
    private double kmPorLitro;
    @Value("${taxa.margem.lucro.base}")
    private double margemLucroBase;
    @Value("${taxa.margem.lucro.intermediaria}")
    private double margemLucroIntermediaria;
    @Value("${taxa.margem.lucro.alta}")
    private double margemLucroAlta;
    @Value("${taxa.distancia.limite.intermediaria}")
    private double distanciaLimiteIntermediaria;
    @Value("${taxa.distancia.limite.alta}")
    private double distanciaLimiteAlta;

    public CalcularDeslocamentoUseCase(DeslocamentoPort deslocamentoPort, CalculadoraTaxa calculadoraTaxa) {
        this.deslocamentoPort = deslocamentoPort;
        this.calculadoraTaxa = calculadoraTaxa;
    }

    public Deslocamento execute(Endereco enderecoDestino) {
        DistanciaETempo distanciaETempo = deslocamentoPort.calcularDistanciaETempo(enderecoDestino);

        CalculadoraTaxa.ParametrosCalculo params = new CalculadoraTaxa.ParametrosCalculo(
                distanciaETempo.distanciaKm(),
                precoGasolina,
                kmPorLitro,
                margemLucroBase,
                margemLucroIntermediaria,
                margemLucroAlta,
                distanciaLimiteIntermediaria,
                distanciaLimiteAlta
        );

        double taxa = calculadoraTaxa.calcular(params);

        return new Deslocamento(distanciaETempo.distanciaKm(), distanciaETempo.tempoMinutos(), taxa);
    }
}
