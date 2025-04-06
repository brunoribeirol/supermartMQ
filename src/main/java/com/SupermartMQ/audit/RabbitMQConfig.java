package com.SupermartMQ.audit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE = "topic-exchange";

    //Fila de auditoria
    @Bean
    public Queue auditQueue() {
        return new Queue("audit.queue");
    }

    @Bean
    public Binding auditBinding(Queue auditQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(auditQueue).to(topicExchange).with("#"); // Escuta tudo
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, false, false);
    }
}
