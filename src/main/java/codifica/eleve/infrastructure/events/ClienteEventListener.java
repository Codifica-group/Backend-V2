package codifica.eleve.infrastructure.events;

import codifica.eleve.core.application.ports.out.ClienteEventPublisherPort;
import codifica.eleve.core.application.usecase.cliente.CreateClienteUseCase;
import codifica.eleve.core.domain.cliente.Cliente;
import codifica.eleve.core.domain.shared.Endereco;
import codifica.eleve.core.domain.shared.Telefone;
import codifica.eleve.infrastructure.events.dto.ClienteCadastradoEvent;
import codifica.eleve.infrastructure.events.dto.FalhaCadastroClienteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClienteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ClienteEventListener.class);
    private final CreateClienteUseCase createClienteUseCase;
    private final ClienteEventPublisherPort clienteEventPublisher;

    public ClienteEventListener(CreateClienteUseCase createClienteUseCase, ClienteEventPublisherPort clienteEventPublisher) {
        this.createClienteUseCase = createClienteUseCase;
        this.clienteEventPublisher = clienteEventPublisher;
    }

    @RabbitListener(queues = "cliente.para-cadastrar.queue")
    public void onClienteParaCadastrar(ClienteParaCadastrarEvent event) {
        logger.info("Recebido evento para cadastrar cliente com chatId: {}", event.getChatId());
        try {
            Telefone telefone = new Telefone(event.getTelefone());
            Endereco endEvent = event.getEndereco();
            Endereco endereco = new Endereco(
                    endEvent.getCep(),
                    endEvent.getRua(),
                    endEvent.getNumero(),
                    endEvent.getBairro(),
                    endEvent.getCidade(),
                    endEvent.getComplemento()
            );
            Cliente cliente = new Cliente(event.getNome(), telefone, endereco);

            Map<String, Object> response = createClienteUseCase.execute(cliente);
            Integer clienteId = (Integer) response.get("id");
            logger.info("Cliente do chatId {} cadastrado com Id: {}", event.getChatId(), clienteId);

            clienteEventPublisher.publicarClienteCadastrado(new ClienteCadastradoEvent(event.getChatId(), clienteId));
        } catch (Exception e) {
            logger.error("Erro ao processar evento de cadastro de cliente para o chatId: {}", event.getChatId(), e);
            clienteEventPublisher.publicarFalhaCadastro(new FalhaCadastroClienteEvent(event.getChatId(), e.getMessage()));
        }
    }
}
