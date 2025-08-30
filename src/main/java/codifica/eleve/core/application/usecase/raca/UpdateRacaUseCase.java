package codifica.eleve.core.application.usecase.raca;

import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateRacaUseCase {
    private final RacaRepository racaRepository;

    public UpdateRacaUseCase(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    public String execute(Integer id, Raca raca) {
        if (!racaRepository.existsById(id)) {
            throw new NotFoundException("Raça não encontrada.");
        }

        raca.setId(new Id(id));
        racaRepository.save(raca);
        return "Raça atualizada com sucesso.";
    }
}