package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.List;
import java.util.stream.Collectors;

public class FindRacasByNomeSemelhanteUseCase {

    private final RacaRepository racaRepository;

    public FindRacasByNomeSemelhanteUseCase(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public List<Raca> execute(String nome) {
        List<Raca> todasRacas = racaRepository.findAll();
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        return todasRacas.stream()
                .map(raca -> {
                    int distance = levenshteinDistance.apply(nome.toLowerCase(), raca.getNome().toLowerCase());
                    return new RacaComDistancia(raca, distance);
                })
                .filter(racaComDistancia -> racaComDistancia.distancia <= 5)
                .sorted()
                .limit(3)
                .map(racaComDistancia -> racaComDistancia.raca)
                .collect(Collectors.toList());
    }

    private static class RacaComDistancia implements Comparable<RacaComDistancia> {
        Raca raca;
        int distancia;

        RacaComDistancia(Raca raca, int distancia) {
            this.raca = raca;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(RacaComDistancia other) {
            return Integer.compare(this.distancia, other.distancia);
        }
    }
}
