package codifica.eleve.core.application.usecase.servico;

import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class FindServicoByIdUseCase {

    private final ServicoRepository servicoRepository;

    public FindServicoByIdUseCase(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico execute(Integer id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Serviço não encontrado."));
    }
}
