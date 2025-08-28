package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;

import java.util.List;

public class ListRacaUseCase {
    private final RacaRepository racaRepository;

    public ListRacaUseCase(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public List<Raca> execute() {
        return racaRepository.findAll();
    }
}