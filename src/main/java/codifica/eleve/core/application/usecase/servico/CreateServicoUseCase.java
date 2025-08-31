package codifica.eleve.core.application.usecase.servico;

import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.exceptions.ConflictException;

import java.util.HashMap;
import java.util.Map;

public class CreateServicoUseCase {
    private final ServicoRepository servicoRepository;

    public CreateServicoUseCase(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Map<String, Object> execute(Servico servico) {
        if (servico.getNome() == null || servico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do serviço nao pode ser vazio.");
        }

        if (servicoRepository.existsByNome(servico.getNome())) {
            throw new ConflictException("Impossível cadastrar dois serviços com o mesmo nome.");
        }

        Servico novoServico = servicoRepository.save(servico);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Serviço cadastrado com sucesso.");
        response.put("id", novoServico.getId().getValue());
        return response;
    }
}
