package com.SupermartMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE = "topic-exchange";

    public static final String MARKET_HUB_ROUTING_KEY = "marketHub.#";
    public static final String FRESH_MARKET_ROUTING_KEY = "freshMarket.#";
    public static final String MARKET_HUB_FRESH_MARKET_ROUTING_KEY = "marketHubFreshMarket.#";

    public static final String MARKET_HUB_QUEUE = "marketHubQueue";
    public static final String FRESH_MARKET_QUEUE = "freshMarketQueue";
    public static final String MARKET_HUB_FRESH_MARKET_QUEUE = "marketHubFreshMarketQueue";

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, false, false);
    }

    @Bean
    Queue marketHubQueue() {
        return new Queue(MARKET_HUB_QUEUE);
    }

    @Bean
    Queue freshMarketQueue() {
        return new Queue(FRESH_MARKET_QUEUE);
    }

    @Bean
    Queue marketHubFreshMarketQueue() {
        return new Queue(MARKET_HUB_FRESH_MARKET_QUEUE);
    }

    @Bean
    Binding marketHubBinding(Queue marketHubQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(marketHubQueue).to(topicExchange).with(MARKET_HUB_ROUTING_KEY);
    }

    @Bean
    Binding freshMarketBinding(Queue freshMarketQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(freshMarketQueue).to(topicExchange).with(FRESH_MARKET_ROUTING_KEY);
    }

    @Bean
    Binding marketHubFreshMarketBinding(Queue marketHubFreshMarketQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(marketHubFreshMarketQueue).to(topicExchange).with(MARKET_HUB_FRESH_MARKET_ROUTING_KEY);
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
