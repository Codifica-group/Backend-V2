package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindRacaByNomeUseCase {
    private final RacaRepository racaRepository;

    public FindRacaByNomeUseCase(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public Raca execute(String nome) {
        return racaRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Raça não encontrada."));
    }
}
