package codifica.eleve.core.application.usecase.agenda.calculadora;

import codifica.eleve.core.domain.agenda.calculadora.SugestaoServico;
import codifica.eleve.core.domain.agenda.deslocamento.Deslocamento;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.pet.PetRepository;
import codifica.eleve.core.domain.servico.Servico;
import codifica.eleve.core.domain.servico.ServicoRepository;
import codifica.eleve.core.domain.shared.ValorMonetario;
import codifica.eleve.core.domain.shared.exceptions.IllegalArgumentException;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CalcularServicoUseCase {

    private final PetRepository petRepository;
    private final ServicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;
    private final CalcularDeslocamentoUseCase calcularDeslocamentoUseCase;
    private static final Logger logger = LoggerFactory.getLogger(CalcularServicoUseCase.class);

    public CalcularServicoUseCase(PetRepository petRepository, ServicoRepository servicoRepository, ClienteRepository clienteRepository, CalcularDeslocamentoUseCase calcularDeslocamentoUseCase) {
        this.petRepository = petRepository;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
        this.calcularDeslocamentoUseCase = calcularDeslocamentoUseCase;
    }

    public SugestaoServico execute(Integer petId, List<Integer> servicosId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado."));

        if (pet.getCliente() == null || pet.getCliente().getId() == null) {
            throw new IllegalArgumentException("Pet não associado a um cliente.");
        }

        Cliente cliente = clienteRepository.findById(pet.getCliente().getId().getValue())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado."));

        if (cliente.getEndereco() == null) {
            throw new IllegalArgumentException("Endereço do cliente não encontrado.");
        }

        Deslocamento deslocamento;
        try {
            deslocamento = calcularDeslocamentoUseCase.execute(cliente.getEndereco());
        } catch (Exception e) {
            logger.error("Erro ao consultar API de deslocamento: {}", e.getMessage());
            deslocamento = new Deslocamento(0.0, 0.0, 0.0);
        }

        List<Servico> servicos = servicosId.stream()
                .map(id -> servicoRepository.findById(id).orElseThrow(() -> new NotFoundException("Serviço não encontrado.")))
                .collect(Collectors.toList());

        BigDecimal valorTotalServicos = BigDecimal.ZERO;
        List<Servico> servicosCalculados = servicos.stream().map(servico -> {
            BigDecimal valorCorrigido = calcularValorServico(servico, pet);
            Servico servicoCalculado = new Servico(servico.getNome(), new ValorMonetario(valorCorrigido));
            servicoCalculado.setId(servico.getId());
            return servicoCalculado;
        }).collect(Collectors.toList());

        for (Servico servico : servicosCalculados) {
            valorTotalServicos = valorTotalServicos.add(servico.getValor().getValor());
        }

        BigDecimal valorTotal = valorTotalServicos.add(BigDecimal.valueOf(deslocamento.getTaxa()));

        return new SugestaoServico(valorTotal, servicosCalculados, deslocamento);
    }

    private BigDecimal calcularValorServico(Servico servico, Pet pet) {
        BigDecimal valorBase = servico.getValor().getValor();
        BigDecimal adicionalPorte = BigDecimal.ZERO;

        if (pet.getRaca() == null || pet.getRaca().getPorte() == null || pet.getRaca().getPorte().getId() == null) {
            throw new IllegalArgumentException("Dados de porte e raça do pet estão incompletos.");
        }

        if (pet.getRaca().getPorte().getId().getValue() > 1) {
            switch (pet.getRaca().getPorte().getId().getValue()) {
                case 2: // Médio
                    adicionalPorte = new BigDecimal("10.0");
                    break;
                case 3: // Grande
                    adicionalPorte = new BigDecimal("20.0");
                    break;
            }
        }
        return valorBase.add(adicionalPorte);
    }
}
