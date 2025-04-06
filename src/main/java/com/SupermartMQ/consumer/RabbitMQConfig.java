package com.SupermartMQ.consumer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE = "topic-exchange";

    public static final String FRESH_MARKET_FRUITS_KEY = "freshMarket.fruits";
    public static final String FRESH_MARKET_MEATFISH_KEY = "freshMarket.meat_fish";
    public static final String FRESH_MARKET_CLEANING_KEY = "freshMarket.cleaning_products";

    public static final String MARKET_HUB_BEVERAGES_KEY = "marketHub.beverages";
    public static final String MARKET_HUB_SNACKS_KEY = "marketHub.snacks";
    public static final String MARKET_HUB_MARKET_BAKERY = "marketHub.bakery";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE, false, false);
    }

    @Bean
    public Queue freshMarketFruitsQueue() {
        return new Queue("freshMarket.fruits.queue");
    }

    @Bean
    public Binding bindingFruits(TopicExchange topicExchange, Queue freshMarketFruitsQueue) {
        return BindingBuilder.bind(freshMarketFruitsQueue)
                .to(topicExchange)
                .with(FRESH_MARKET_FRUITS_KEY);
    }

    @Bean
    public Queue freshMarketMeatFishQueue() {
        return new Queue("freshMarket.meat_fish.queue");
    }

    @Bean
    public Binding bindingMeatFish(TopicExchange topicExchange, Queue freshMarketMeatFishQueue) {
        return BindingBuilder.bind(freshMarketMeatFishQueue)
                .to(topicExchange)
                .with(FRESH_MARKET_MEATFISH_KEY);
    }

    @Bean
    public Queue freshMarketCleaningQueue() {
        return new Queue("freshMarket.cleaning_products.queue");
    }

    @Bean
    public Binding bindingCleaning(TopicExchange topicExchange, Queue freshMarketCleaningQueue) {
        return BindingBuilder.bind(freshMarketCleaningQueue)
                .to(topicExchange)
                .with(FRESH_MARKET_CLEANING_KEY);
    }

    @Bean
    public Queue marketHubBeveragesQueue() {
        return new Queue("marketHub.beverages.queue");
    }

    @Bean
    public Binding bindingBeverages(TopicExchange topicExchange, Queue marketHubBeveragesQueue) {
        return BindingBuilder.bind(marketHubBeveragesQueue)
                .to(topicExchange)
                .with(MARKET_HUB_BEVERAGES_KEY);
    }

    @Bean
    public Queue marketHubSnacksQueue() {
        return new Queue("marketHub.snacks.queue");
    }

    @Bean
    public Binding bindingSnacks(TopicExchange topicExchange, Queue marketHubSnacksQueue) {
        return BindingBuilder.bind(marketHubSnacksQueue)
                .to(topicExchange)
                .with(MARKET_HUB_SNACKS_KEY);
    }

    @Bean
    public Queue marketHubBakeryQueue() {
        return new Queue("marketHub.bakery.queue");
    }

    @Bean
    public Binding bindingBakery(TopicExchange topicExchange, Queue marketHubBakeryQueue) {
        return BindingBuilder.bind(marketHubBakeryQueue)
                .to(topicExchange)
                .with(MARKET_HUB_MARKET_BAKERY);
    }

    @Bean
    public ConsumerReceiver receiver() {
        return new ConsumerReceiver();
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(ConsumerReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
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