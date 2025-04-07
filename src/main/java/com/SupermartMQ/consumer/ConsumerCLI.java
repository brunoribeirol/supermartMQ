package com.SupermartMQ.consumer;

import java.util.Scanner;
import java.util.UUID;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.ApplicationContext;

@Configuration
public class ConsumerCLI {

    @Bean
    CommandLineRunner runner(SimpleMessageListenerContainer container, ApplicationContext context) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            String selectedSupermarket = "";
            String selectedSector = "";
            String routingKey = "";

            while (true) {
                System.out.println("\n🛒 Choose your supermarket:");
                System.out.println("[1] MarketHub");
                System.out.println("[2] FreshMarket");
                System.out.print("Enter your choice (1-2): ");
                String supermarketChoice = scanner.nextLine().trim();

                if (supermarketChoice.equals("1")) {
                    selectedSupermarket = "MarketHub";
                    break;
                } else if (supermarketChoice.equals("2")) {
                    selectedSupermarket = "FreshMarket";
                    break;
                } else {
                    System.out.println("❌ Invalid option. Try again.");
                }
            }

            if (selectedSupermarket.equals("MarketHub")) {
                while (true) {
                    System.out.println("\n📦 Choose a MarketHub sector:");
                    System.out.println("[1] Beverages");
                    System.out.println("[2] Snacks");
                    System.out.println("[3] Bakery");
                    System.out.print("Enter your choice (1-3): ");
                    String hubChoice = scanner.nextLine().trim();

                    switch (hubChoice) {
                        case "1" -> {
                            selectedSector = "Beverages";
                            routingKey = "marketHub.beverages";
                        }
                        case "2" -> {
                            selectedSector = "Snacks";
                            routingKey = "marketHub.snacks";
                        }
                        case "3" -> {
                            selectedSector = "Bakery";
                            routingKey = "marketHub.bakery";
                        }
                        default -> {
                            System.out.println("❌ Invalid sector. Try again.");
                            continue;
                        }
                    }
                    break;
                }
            } else {
                while (true) {
                    System.out.println("\n📦 Choose a FreshMarket sector:");
                    System.out.println("[1] Meat & Fish");
                    System.out.println("[2] Fruits");
                    System.out.println("[3] Cleaning Products");
                    System.out.print("Enter your choice (1-3): ");
                    String freshChoice = scanner.nextLine().trim();

                    switch (freshChoice) {
                        case "1" -> {
                            selectedSector = "Meat & Fish";
                            routingKey = "freshMarket.meat_fish";
                        }
                        case "2" -> {
                            selectedSector = "Fruits";
                            routingKey = "freshMarket.fruits";
                        }
                        case "3" -> {
                            selectedSector = "Cleaning Products";
                            routingKey = "freshMarket.cleaning_products";
                        }
                        default -> {
                            System.out.println("❌ Invalid sector. Try again.");
                            continue;
                        }
                    }
                    break;
                }
            }

            String uniqueQueue = routingKey + ".client_" + UUID.randomUUID();

            Queue queue = new Queue(uniqueQueue, false, false, true);
            AmqpAdmin admin = context.getBean(AmqpAdmin.class);
            TopicExchange exchange = context.getBean(TopicExchange.class);

            admin.declareQueue(queue);
            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));

            container.stop();
            container.setQueueNames(uniqueQueue);
            container.start();

            System.out.println("\n✅ You selected:");
            System.out.println("Supermarket: " + selectedSupermarket);
            System.out.println("Sector: " + selectedSector);
            System.out.println("🎧 Listening on queue: " + uniqueQueue);
            System.out.println("⏳ Waiting for messages...\n");
        };
    }
}
