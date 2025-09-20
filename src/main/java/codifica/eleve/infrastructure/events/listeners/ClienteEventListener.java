package codifica.eleve.infrastructure.events.listeners;

import codifica.eleve.core.application.usecase.events.ClienteParaCadastrarUseCase;
import codifica.eleve.core.domain.events.cliente.ClienteParaCadastrarEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventListener {

    private final ClienteParaCadastrarUseCase clienteParaCadastrarUseCase;

    public ClienteEventListener(ClienteParaCadastrarUseCase clienteParaCadastrarUseCase) {
        this.clienteParaCadastrarUseCase = clienteParaCadastrarUseCase;
    }

    @RabbitListener(queues = "cliente.para-cadastrar.queue")
    public void onClienteParaCadastrar(ClienteParaCadastrarEvent event) {
        clienteParaCadastrarUseCase.processClienteParaCadastrar(event);
    }
}
