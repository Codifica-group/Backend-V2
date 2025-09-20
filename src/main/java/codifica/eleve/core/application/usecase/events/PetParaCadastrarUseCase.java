package codifica.eleve.core.application.usecase.events;

import codifica.eleve.core.application.ports.in.events.PetEventListenerPort;
import codifica.eleve.core.application.ports.out.events.PetEventPublisherPort;
import codifica.eleve.core.application.usecase.pet.CreatePetUseCase;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.cliente.ClienteRepository;
import codifica.eleve.core.domain.events.pet.PetParaCadastrarEvent;
import codifica.eleve.core.domain.events.pet.PetParaCadastrarResponseEvent;
import codifica.eleve.core.domain.pet.Pet;
import codifica.eleve.core.domain.raca.Raca;
import codifica.eleve.core.domain.raca.RacaRepository;
import codifica.eleve.core.domain.shared.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PetParaCadastrarUseCase implements PetEventListenerPort {

    private static final Logger logger = LoggerFactory.getLogger(PetParaCadastrarUseCase.class);

    private final RacaRepository racaRepository;
    private final ClienteRepository clienteRepository;
    private final CreatePetUseCase createPetUseCase;
    private final PetEventPublisherPort petEventPublisher;

    public PetParaCadastrarUseCase(
            RacaRepository racaRepository,
            ClienteRepository clienteRepository,
            CreatePetUseCase createPetUseCase,
            PetEventPublisherPort petEventPublisher) {
        this.racaRepository = racaRepository;
        this.clienteRepository = clienteRepository;
        this.createPetUseCase = createPetUseCase;
        this.petEventPublisher = petEventPublisher;
    }

    @Override
    public void processPetParaCadastrar(PetParaCadastrarEvent event) {
        logger.info("EVENTO: Pet Para Cadastrar com chatId: {}", event.getChatId());
        try {
            Cliente cliente = clienteRepository.findById(event.getClienteId())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado."));

            // TODO: Criar tratativa para Raça não encontrada.
            Raca raca = racaRepository.findByNome(event.getRaca())
                    .orElseThrow(() -> new NotFoundException("Raça nao encontrada."));

            Pet pet = new Pet(event.getNome(), raca, cliente);
            Map<String, Object> response = createPetUseCase.execute(pet);
            Integer petId = (Integer) response.get("id");
            logger.info("SUCESSO: Pet do chatId {} cadastrado com Id: {}", event.getChatId(), petId);

            petEventPublisher.publishPetParaCadastrarResponse(
                    PetParaCadastrarResponseEvent.sucesso(event.getChatId(), event.getClienteId(), petId)
            );

        } catch (Exception e) {
            logger.error("FALHA: Erro ao cadastrar pet do chatId {}: {}", event.getChatId(), e.getMessage());

            petEventPublisher.publishPetParaCadastrarResponse(
                    PetParaCadastrarResponseEvent.falha(event.getChatId(), e.getMessage())
            );
        }
    }
}
