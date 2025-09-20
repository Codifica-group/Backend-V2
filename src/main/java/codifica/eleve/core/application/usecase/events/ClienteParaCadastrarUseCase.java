package codifica.eleve.core.application.usecase.events;

import codifica.eleve.core.application.ports.in.events.ClienteEventListenerPort;
import codifica.eleve.core.application.ports.out.events.ClienteEventPublisherPort;
import codifica.eleve.core.application.usecase.cliente.CreateClienteUseCase;
import codifica.eleve.core.application.usecase.cliente.cep.FindCepUseCase;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.events.cliente.ClienteParaCadastrarEvent;
import codifica.eleve.core.domain.events.cliente.ClienteParaCadastrarResponseEvent;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.Telefone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ClienteParaCadastrarUseCase implements ClienteEventListenerPort {

    private static final Logger logger = LoggerFactory.getLogger(ClienteParaCadastrarUseCase.class);

    private final FindCepUseCase findCepUseCase;
    private final CreateClienteUseCase createClienteUseCase;
    private final ClienteEventPublisherPort clienteEventPublisher;

    public ClienteParaCadastrarUseCase(
            FindCepUseCase findCepUseCase,
            CreateClienteUseCase createClienteUseCase,
            ClienteEventPublisherPort clienteEventPublisher) {
        this.findCepUseCase = findCepUseCase;
        this.createClienteUseCase = createClienteUseCase;
        this.clienteEventPublisher = clienteEventPublisher;
    }

    @Override
    public void processClienteParaCadastrar(ClienteParaCadastrarEvent event) {
        logger.info("EVENTO: Cliente Para Cadastrar com chatId: {}", event.getChatId());
        try {
            Endereco enderecoBase = findCepUseCase.execute(event.getCep());
            Endereco enderecoCompleto = new Endereco(
                    enderecoBase.getCep(),
                    enderecoBase.getRua(),
                    event.getNumeroEndereco(),
                    enderecoBase.getBairro(),
                    enderecoBase.getCidade(),
                    event.getComplemento()
            );

            Telefone telefone = new Telefone(event.getTelefone());
            Cliente cliente = new Cliente(event.getNome(), telefone, enderecoCompleto);

            Map<String, Object> response = createClienteUseCase.execute(cliente);
            Integer clienteId = (Integer) response.get("id");
            logger.info("SUCESSO: Cliente do chatId {} cadastrado com Id: {}", event.getChatId(), clienteId);

            clienteEventPublisher.publishClienteParaCadastrarResponse(
                    ClienteParaCadastrarResponseEvent.sucesso(event.getChatId(), clienteId)
            );

        } catch (Exception e) {
            logger.error("FALHA: Erro ao cadastrar cliente do chatId {}: {}", event.getChatId(), e.getMessage());

            clienteEventPublisher.publishClienteParaCadastrarResponse(
                    ClienteParaCadastrarResponseEvent.falha(event.getChatId(), e.getMessage())
            );
        }
    }
}
