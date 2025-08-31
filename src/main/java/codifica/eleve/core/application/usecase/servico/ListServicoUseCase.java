package codifica.eleve.core.application.usecase.servico;

import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;

import java.util.List;

public class ListServicoUseCase {
    private final ServicoRepository servicoRepository;

    public ListServicoUseCase(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> execute() {
        return servicoRepository.findAll();
    }
}
