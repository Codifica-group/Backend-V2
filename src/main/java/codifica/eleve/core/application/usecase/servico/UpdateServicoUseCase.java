package codifica.eleve.core.application.usecase.servico;

import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.Id;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;

public class UpdateServicoUseCase {
    private final ServicoRepository servicoRepository;

    public UpdateServicoUseCase(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public String execute(Integer id, Servico servico) {
        if (servico.getNome() == null || servico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do serviço nao pode ser vazio.");
        }
        if (!servicoRepository.existsById(id)) {
            throw new NotFoundException("Serviço não encontrado.");
        }
        if (servicoRepository.existsByNome(servico.getNome())) {
            throw new ConflictException("Já existe um serviço com o mesmo nome.");
        }

        servico.setId(new Id(id));
        servicoRepository.save(servico);
        return "Serviço atualizado com sucesso.";
    }
}
