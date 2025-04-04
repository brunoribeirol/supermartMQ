package com.SupermartMQ;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;


@SpringBootApplication
public class Consumer {

    private static final String EXCHANGE_NAME = "topic-exchange";

    @Bean
    Queue marketHubQueue() {
        return new Queue("marketHubQueue", true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding marketHubBinding(Queue marketHubQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(marketHubQueue).to(topicExchange).with("marketHub");
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
        container.setQueueNames("marketHubQueue");
        container.setMessageListener(listenerAdapter);
        return container;
    }

    public static void main(String[] args) {
        SpringApplication.run(Consumer.class, args);
    }
}

