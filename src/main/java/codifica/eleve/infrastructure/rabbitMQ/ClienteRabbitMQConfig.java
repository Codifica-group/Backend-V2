package codifica.eleve.infrastructure.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteRabbitMQConfig {

    public static final String QUEUE_CLIENTE_PARA_CADASTRAR = "cliente.para-cadastrar.queue";
    public static final String ROUTING_KEY_CLIENTE_PARA_CADASTRAR = "cliente.para-cadastrar";
    public static final String QUEUE_CLIENTE_RESPONSE = "cliente.cadastro.response.queue";
    public static final String ROUTING_KEY_CLIENTE_RESPONSE = "cliente.cadastro.response";

    @Bean
    public Queue clienteParaCadastrarQueue() {
        return new Queue(QUEUE_CLIENTE_PARA_CADASTRAR, true);
    }

    @Bean
    public Binding clienteParaCadastrarBinding(Queue clienteParaCadastrarQueue, TopicExchange exchange) {
        return BindingBuilder.bind(clienteParaCadastrarQueue).to(exchange).with(ROUTING_KEY_CLIENTE_PARA_CADASTRAR);
    }
}
