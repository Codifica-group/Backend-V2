package codifica.eleve.core.application.usecase.servico;

import codifica.eleve.core.domain.agenda.AgendaRepository;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class DeleteServicoUseCase {
    private final ServicoRepository servicoRepository;
    private final AgendaRepository agendaRepository;

    public DeleteServicoUseCase(ServicoRepository servicoRepository, AgendaRepository agendaRepository) {
        this.servicoRepository = servicoRepository;
        this.agendaRepository = agendaRepository;
    }

    public void execute(Integer id) {
        if (!servicoRepository.existsById(id)) {
            throw new NotFoundException("Serviço não encontrado.");
        }

        if (agendaRepository.existsByServicoId(id)) {
            throw new ConflictException("Não é possível deletar um serviço com agenda cadastrada.");
        }

        servicoRepository.deleteById(id);
    }
}
