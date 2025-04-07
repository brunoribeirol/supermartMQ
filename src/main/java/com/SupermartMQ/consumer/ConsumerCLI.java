package com.SupermartMQ.consumer;

import java.util.Scanner;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerCLI {

    @Bean
    CommandLineRunner runner(SimpleMessageListenerContainer container) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            String selectedSupermarket = "";
            String selectedSector = "";
            String queueName = "";

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
                        case "1":
                            selectedSector = "Beverages";
                            queueName = "marketHub.beverages.queue";
                            break;
                        case "2":
                            selectedSector = "Snacks";
                            queueName = "marketHub.snacks.queue";
                            break;
                        case "3":
                            selectedSector = "Bakery";
                            queueName = "marketHub.bakery.queue";
                            break;
                        default:
                            System.out.println("❌ Invalid sector. Try again.");
                            continue;
                    }
                    break;
                }
            } else if (selectedSupermarket.equals("FreshMarket")) {
                while (true) {
                    System.out.println("\n📦 Choose a FreshMarket sector:");
                    System.out.println("[1] Meat & Fish");
                    System.out.println("[2] Fruits");
                    System.out.println("[3] Cleaning Products");
                    System.out.print("Enter your choice (1-3): ");
                    String freshChoice = scanner.nextLine().trim();

                    switch (freshChoice) {
                        case "1":
                            selectedSector = "Meat & Fish";
                            queueName = "freshMarket.meat_fish.queue";
                            break;
                        case "2":
                            selectedSector = "Fruits";
                            queueName = "freshMarket.fruits.queue";
                            break;
                        case "3":
                            selectedSector = "Cleaning Products";
                            queueName = "freshMarket.cleaning_products.queue";
                            break;
                        default:
                            System.out.println("❌ Invalid sector. Try again.");
                            continue;
                    }
                    break;
                }
            }

            container.stop();
            container.setQueueNames(queueName);
            container.start();

            System.out.println("\n✅ You selected:");
            System.out.println("Supermarket: " + selectedSupermarket);
            System.out.println("Sector: " + selectedSector);
            System.out.println("🎧 Listening to: " + queueName);
            System.out.println("⏳ Waiting for messages...\n");
        };
    }
}