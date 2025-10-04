package codifica.eleve.infrastructure.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolicitacaoRabbitMQConfig {

    public static final String QUEUE_SOLICITACAO_PARA_CADASTRAR = "solicitacao.para-cadastrar.queue";
    public static final String ROUTING_KEY_SOLICITACAO_PARA_CADASTRAR = "solicitacao.para-cadastrar";
    public static final String QUEUE_SOLICITACAO_RESPONSE = "solicitacao.cadastro.response.queue";
    public static final String ROUTING_KEY_SOLICITACAO_RESPONSE = "solicitacao.cadastro.response";
    public static final String QUEUE_SOLICITACAO_ATUALIZADA = "solicitacao.atualizada.queue";
    public static final String ROUTING_KEY_SOLICITACAO_ATUALIZADA = "solicitacao.atualizada";
    public static final String QUEUE_SOLICITacao_ACEITA = "solicitacao.aceita.queue";
    public static final String ROUTING_KEY_SOLICITACAO_ACEITA = "solicitacao.aceita";
    public static final String QUEUE_SOLICITACAO_ACEITA_RESPONSE = "solicitacao.aceita.response.queue";
    public static final String ROUTING_KEY_SOLICITACAO_ACEITA_RESPONSE = "solicitacao.aceita.response";

    @Bean
    public Queue solicitacaoParaCadastrarQueue() {
        return new Queue(QUEUE_SOLICITACAO_PARA_CADASTRAR, true);
    }

    @Bean
    public Binding solicitacaoParaCadastrarBinding(Queue solicitacaoParaCadastrarQueue, TopicExchange exchange) {
        return BindingBuilder.bind(solicitacaoParaCadastrarQueue).to(exchange).with(ROUTING_KEY_SOLICITACAO_PARA_CADASTRAR);
    }

    @Bean
    public Queue solicitacaoAtualizadaQueue() {
        return new Queue(QUEUE_SOLICITACAO_ATUALIZADA, true);
    }

    @Bean
    public Binding solicitacaoAtualizadaBinding(Queue solicitacaoAtualizadaQueue, TopicExchange exchange) {
        return BindingBuilder.bind(solicitacaoAtualizadaQueue).to(exchange).with(ROUTING_KEY_SOLICITACAO_ATUALIZADA);
    }

    @Bean
    public Queue solicitacaoAceitaQueue() {
        return new Queue(QUEUE_SOLICITacao_ACEITA, true);
    }

    @Bean
    public Binding solicitacaoAceitaBinding(Queue solicitacaoAceitaQueue, TopicExchange exchange) {
        return BindingBuilder.bind(solicitacaoAceitaQueue).to(exchange).with(ROUTING_KEY_SOLICITACAO_ACEITA);
    }
}
