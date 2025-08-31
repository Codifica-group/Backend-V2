package codifica.eleve.core.application.usecase.servico;

import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteServicoUseCase {
    private final ServicoRepository servicoRepository;

    public DeleteServicoUseCase(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public void execute(Integer id) {
        if (!servicoRepository.existsById(id)) {
            throw new NotFoundException("Serviço não encontrado.");
        }

        servicoRepository.deleteById(id);
    }
}
