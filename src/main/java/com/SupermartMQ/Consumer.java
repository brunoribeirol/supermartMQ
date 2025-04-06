//package com.SupermartMQ;
//
//import java.util.Scanner;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//@SpringBootApplication
//public class Consumer {
//
//    private static final String TOPIC_EXCHANGE = "topic-exchange";
//
//    private static final String MARKET_HUB_ROUTING_KEY = "marketHub.#";
//    private static final String FRESH_MARKET_ROUTING_KEY = "freshMarket.#";
//    private static final String MARKET_HUB_FRESH_MARKET_ROUTING_KEY = "marketHubFreshMarket.#";
//
//    private static final String MARKET_HUB_QUEUE = "marketHubQueue";
//    private static final String FRESH_MARKET_QUEUE = "freshMarketQueue";
//    private static final String MARKET_HUB_FRESH_MARKET_QUEUE = "marketHubFreshMarketQueue";
//
//    public static void main(String[] args) {
//        SpringApplication.run(Consumer.class, args);
//    }
//
//    // Exchange
//    @Bean
//    TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE, false, false);
//    }
//
//    // Queues
//    @Bean
//    Queue marketHubQueue() {
//        return new Queue(MARKET_HUB_QUEUE);
//    }
//
//    @Bean
//    Queue freshMarketQueue() {
//        return new Queue(FRESH_MARKET_QUEUE);
//    }
//
//    @Bean
//    Queue marketHubFreshMarketQueue() {
//        return new Queue(MARKET_HUB_FRESH_MARKET_QUEUE);
//    }
//
//    // Bindings
//    @Bean
//    Binding marketHubBinding(Queue marketHubQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(marketHubQueue).to(topicExchange).with(MARKET_HUB_ROUTING_KEY);
//    }
//
//    @Bean
//    Binding freshMarketBinding(Queue freshMarketQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(freshMarketQueue).to(topicExchange).with(FRESH_MARKET_ROUTING_KEY);
//    }
//
//    @Bean
//    Binding marketHubFreshMarketBinding(Queue marketHubFreshMarketQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(marketHubFreshMarketQueue).to(topicExchange).with(MARKET_HUB_FRESH_MARKET_ROUTING_KEY);
//    }
//
//    // Receiver
//    @Bean
//    Receiver receiver() {
//        return new Receiver();
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
//
//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    // CLI for user interaction
//    @Bean
//    CommandLineRunner runner(SimpleMessageListenerContainer container,
//                             Queue marketHubQueue,
//                             Queue freshMarketQueue,
//                             Queue marketHubFreshMarketQueue) {
//        return args -> {
//            Scanner scanner = new Scanner(System.in);
//            Queue selectedQueue;
//            String selectedSupermarket = "";
//            String selectedSector = "";
//
//            while (true) {
//                System.out.println("\nWhich supermarket do you wanna see the discounts?");
//                System.out.println("[1] - MarketHub");
//                System.out.println("[2] - FreshMarket");
//                System.out.println("[3] - MarketHub & FreshMarket");
//                System.out.print("Enter your choice (1-3): ");
//                String supermarketChoice = scanner.nextLine();
//
//                switch (supermarketChoice) {
//                    case "1":
//                        selectedQueue = marketHubQueue;
//                        selectedSupermarket = "MarketHub";
//                        break;
//                    case "2":
//                        selectedQueue = freshMarketQueue;
//                        selectedSupermarket = "FreshMarket";
//                        break;
//                    case "3":
//                        selectedQueue = marketHubFreshMarketQueue;
//                        selectedSupermarket = "MarketHub & FreshMarket";
//                        break;
//                    default:
//                        System.out.println("❌ Invalid supermarket!");
//                        continue;
//                }
//
//                System.out.println("\nWhich sector do you wanna see the discounts?");
//                System.out.println("[1] - Beverages");
//                System.out.println("[2] - Fruits");
//                System.out.println("[3] - Cleaning Products");
////            System.out.println("[4] - Snacks");
////            System.out.println("[5] - Dairy Products");
////            System.out.println("[6] - Bakery");
////            System.out.println("[7] - Frozen Foods");
////            System.out.println("[8] - Meat And Poultry");
////            System.out.println("[9] - Seafood");
////            System.out.println("[10] - Vegetables");
////            System.out.println("[11] - Personal Care");
////            System.out.println("[12] - Household Items");
////            System.out.println("[13] - Baby Products");
////            System.out.println("[14] - Pet Supplies");
////            System.out.println("[15] - Health And Wellness");
//                String sectorChoice = scanner.nextLine();
//
//                String sectorName;
//
//                switch (sectorChoice) {
//                    case "1":
//                        sectorName = "Beverages";
//                        break;
//                    case "2":
//                        sectorName = "Fruits";
//                        break;
//                    case "3":
//                        sectorName = "Cleaning Products";
//                        break;
////                case "4":
////                    sectorName = "Snacks";
////                    break;
////                case "5":
////                    sectorName = "Dairy Products";
////                    break;
////                case "6":
////                    sectorName = "Bakery";
////                    break;
////                case "7":
////                    sectorName = "Frozen Foods";
////                    break;
////                case "8":
////                    sectorName = "Meat And Poultry";
////                    break;
////                case "9":
////                    sectorName = "Seafood";
////                    break;
////                case "10":
////                    sectorName = "Vegetables";
////                    break;
////                case "11":
////                    sectorName = "Personal Care";
////                    break;
////                case "12":
////                    sectorName = "Household Items";
////                    break;
////                case "13":
////                    sectorName = "Baby Products";
////                    break;
////                case "14":
////                    sectorName = "Pet Supplies";
////                    break;
////                case "15":
////                    sectorName = "Health And Wellness";
////                    break;
//                    default:
//                        System.out.println("❌ Invalid sector!");
//                        continue; // Restart loop
//                }
//
//                // ✅ Set the selected queue dynamically
//                container.setQueues(selectedQueue);
//                container.start(); // Start consuming
//
//                System.out.println("\n✅ You chose:");
//                System.out.println("Supermarket: " + selectedSupermarket);
//                System.out.println("Sector: " + selectedSector);
//                break;
//            }
//        };
//    }
//}