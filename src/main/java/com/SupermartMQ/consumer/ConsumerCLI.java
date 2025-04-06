//package com.SupermartMQ.consumer;
//
//import java.util.Scanner;
//import java.util.concurrent.*;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ConsumerCLI {
//
//    @Bean
//    CommandLineRunner runner(SimpleMessageListenerContainer container) {
//        return args -> {
//            Scanner scanner = new Scanner(System.in);
//            boolean keepRunning = true;
//
//            while (keepRunning) {
//                String selectedSupermarket = "";
//                String selectedSector = "";
//                String queueName = "";
//
//                System.out.println("\n🛒 Choose your supermarket:");
//                System.out.println("[1] MarketHub");
//                System.out.println("[2] FreshMarket");
//                System.out.println("[3] Exit");
//                System.out.print("Enter your choice (1-3): ");
//                String supermarketChoice = scanner.nextLine().trim();
//
//                if (!supermarketChoice.matches("[1-3]")) {
//                    System.out.println("❌ Invalid supermarket. Please try again.");
//                    continue;
//                }
//
//                if (supermarketChoice.equals("3")) {
//                    System.out.println("👋 Exiting...");
//                    break;
//                } else if (supermarketChoice.equals("1")) {
//                    selectedSupermarket = "MarketHub";
//
//                    System.out.println("\n📦 Choose a MarketHub sector:");
//                    System.out.println("[1] Beverages");
//                    System.out.println("[2] Snacks");
//                    System.out.println("[3] Bakery");
//                    System.out.print("Enter your choice (1-3): ");
//                    String hubChoice = scanner.nextLine().trim();
//
//                    if (!hubChoice.matches("[1-3]")) {
//                        System.out.println("❌ Invalid sector. Please try again.");
//                        continue;
//                    }
//
//                    switch (hubChoice) {
//                        case "1":
//                            selectedSector = "Beverages";
//                            queueName = "marketHub.beverages.queue";
//                            break;
//                        case "2":
//                            selectedSector = "Snacks";
//                            queueName = "marketHub.snacks.queue";
//                            break;
//                        case "3":
//                            selectedSector = "Bakery";
//                            queueName = "marketHub.bakery.queue";
//                            break;
//                    }
//
//                } else if (supermarketChoice.equals("2")) {
//                    selectedSupermarket = "FreshMarket";
//
//                    System.out.println("\n📦 Choose a FreshMarket sector:");
//                    System.out.println("[1] Meat & Fish");
//                    System.out.println("[2] Fruits");
//                    System.out.println("[3] Cleaning Products");
//                    System.out.print("Enter your choice (1-3): ");
//                    String freshChoice = scanner.nextLine().trim();
//
//                    if (!freshChoice.matches("[1-3]")) {
//                        System.out.println("❌ Invalid sector. Please try again.");
//                        continue;
//                    }
//
//                    switch (freshChoice) {
//                        case "1":
//                            selectedSector = "Meat & Fish";
//                            queueName = "freshMarket.meat_fish.queue";
//                            break;
//                        case "2":
//                            selectedSector = "Fruits";
//                            queueName = "freshMarket.fruits.queue";
//                            break;
//                        case "3":
//                            selectedSector = "Cleaning Products";
//                            queueName = "freshMarket.cleaning_products.queue";
//                            break;
//                    }
//                }
//
//                System.out.println("\n✅ You selected:");
//                System.out.println("Supermarket: " + selectedSupermarket);
//                System.out.println("Sector: " + selectedSector);
//                System.out.println("🎧 Listening to: " + queueName);
//                System.out.println("⏳ Will stop after 30 seconds of inactivity...\n");
//
//                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//
//                Runnable stopListening = () -> {
//                    System.out.println("⏹️ No messages for 30 seconds. Stopping listener.");
//                    container.stop();
//                };
//
//                ScheduledFuture<?>[] timerTask = new ScheduledFuture<?>[1]; // hold reference to restart task
//
//                container.stop(); // just in case
//                container.setQueueNames(queueName);
//
//                container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
//                    System.out.println("📨 Message received: " + new String(message.getBody()));
//
//                    // Reset timer
//                    if (timerTask[0] != null && !timerTask[0].isDone()) {
//                        timerTask[0].cancel(false);
//                    }
//                    timerTask[0] = scheduler.schedule(stopListening, 30, TimeUnit.SECONDS);
//                });
//
//                container.start();
//
//                // Initial 30-second timer (in case no message comes at all)
//                timerTask[0] = scheduler.schedule(stopListening, 30, TimeUnit.SECONDS);
//
//                // Wait until container stops before continuing
//                while (container.isRunning()) {
//                    Thread.sleep(1000);
//                }
//
//                scheduler.shutdownNow();
//
//                while (true) {
//                    System.out.println("\n🔁 Do you want to check another sector?");
//                    System.out.println("[Y] Yes");
//                    System.out.println("[N] No");
//                    System.out.print("Enter your choice (Y/N): ");
//                    String checkAgain = scanner.nextLine().trim().toLowerCase();
//
//                    if (checkAgain.equals("y")) {
//                        break;
//                    } else if (checkAgain.equals("n")) {
//                        System.out.println("👋 Exiting...");
//                        keepRunning = false;
//                        container.stop(); // stop the listener
//                        System.exit(0);   // force app to exit
//                        break;
//
//                    } else {
//                        System.out.println("❌ Invalid input. Please enter 'Y' or 'N'.");
//                    }
//                }
//            }
//
//            scanner.close();
//        };
//    }
//}






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

//            while (true) {
                String selectedSupermarket = "";
                String selectedSector = "";
                String queueName = "";

                // Ask for supermarket
                System.out.println("\n🛒 Choose your supermarket:");
                System.out.println("[1] MarketHub");
                System.out.println("[2] FreshMarket");
                System.out.print("Enter your choice (1-2): ");
                String supermarketChoice = scanner.nextLine().trim();

                if (supermarketChoice.equals("1")) {
                    selectedSupermarket = "MarketHub";

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
                            System.out.println("❌ Invalid sector. Please try again.");
                            //continue;
                    }

                } else if (supermarketChoice.equals("2")) {
                    selectedSupermarket = "FreshMarket";

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
                            System.out.println("❌ Invalid sector. Please try again.");
                            break;
//                            continue;
                    }

                } else {
                    System.out.println("❌ Invalid supermarket. Please try again.");
                    //continue;
                }


                // Start listening to the selected queue
                container.stop(); // Stop any previously running listener
                container.setQueueNames(queueName);
                container.start();

                System.out.println("\n✅ You selected:");
                System.out.println("Supermarket: " + selectedSupermarket);
                System.out.println("Sector: " + selectedSector);
                System.out.println("🎧 Listening to: " + queueName);
                System.out.println("⏳ Waiting for messages...\n");
//            }
        };
    }
}

