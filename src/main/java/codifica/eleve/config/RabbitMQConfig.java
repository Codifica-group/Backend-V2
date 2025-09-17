package codifica.eleve.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "cliente_exchange";
    public static final String QUEUE_CLIENTE_PARA_CADASTRAR = "cliente.para-cadastrar.queue";
    public static final String ROUTING_KEY_CLIENTE_PARA_CADASTRAR = "cliente.para-cadastrar";
    public static final String QUEUE_CLIENTE_RESPONSE = "cliente.cadastro.response.queue";
    public static final String ROUTING_KEY_CLIENTE_RESPONSE = "cliente.cadastro.response";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue clienteParaCadastrarQueue() {
        return new Queue(QUEUE_CLIENTE_PARA_CADASTRAR, true);
    }

    @Bean
    Binding clienteParaCadastrarBinding(Queue clienteParaCadastrarQueue, TopicExchange exchange) {
        return BindingBuilder.bind(clienteParaCadastrarQueue).to(exchange).with(ROUTING_KEY_CLIENTE_PARA_CADASTRAR);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
