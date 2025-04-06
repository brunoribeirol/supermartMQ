package com.SupermartMQ;

import java.util.Scanner;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.amqp.core.Queue;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.ApplicationContext;


@Configuration
public class ConsumerCLI {

    @Bean
    CommandLineRunner runner(SimpleMessageListenerContainer container,
                             Queue marketHubQueue,
                             Queue freshMarketQueue,
                             Queue marketHubFreshMarketQueue,
                             ApplicationContext context) { //adicionei
        return args -> {
            Scanner scanner = new Scanner(System.in);
//            Queue selectedQueue;
            String selectedSupermarket = "";
            String selectedSector = "";
            String routingKey = "";

            while (true) {
                System.out.println("\nWhich supermarket do you wanna see the discounts?");
                System.out.println("[1] - MarketHub");
                System.out.println("[2] - FreshMarket");
                System.out.println("[3] - MarketHub & FreshMarket");
                System.out.print("Enter your choice (1-3): ");
                String supermarketChoice = scanner.nextLine();

                switch (supermarketChoice) {
                    case "1":
//                        selectedQueue = marketHubQueue;
                        selectedSupermarket = "MarketHub";
                        routingKey = "marketHub";
                        break;
                    case "2":
//                        selectedQueue = freshMarketQueue;
                        selectedSupermarket = "FreshMarket";
                        routingKey = "freshMarket";
                        break;
                    case "3":
//                        selectedQueue = marketHubFreshMarketQueue;
                        selectedSupermarket = "MarketHub & FreshMarket";
                        routingKey = "marketHubFreshMarket";
                        break;
                    default:
                        System.out.println("❌ Invalid supermarket!");
                        continue;
                }

                System.out.println("\nWhich sector do you wanna see the discounts?");
                System.out.println("[1] - Meat & Fish");
                System.out.println("[2] - Fruits");
                System.out.println("[3] - Cleaning Products");

                String sectorChoice = scanner.nextLine();

                switch (sectorChoice) {
                    case "1":
                        selectedSector = "Meat & Fish";
                        routingKey += ".meat_fish";
                        break;
                    case "2":
                        selectedSector = "Fruits";
                        routingKey += ".fruits";
                        break;
                    case "3":
                        selectedSector = "Cleaning Products";
                        routingKey += ".cleaning_products";
                        break;
                    default:
                        System.out.println("❌ Invalid sector!");
                        continue;
                }

                // ✅ Corrigido: criar fila com base apenas na routingKey
                String queueName = "queue." + routingKey.replace(".", "_");


                // Declarar fila e binding dinamicamente
                AmqpAdmin admin = context.getBean(AmqpAdmin.class);
                Queue dynamicQueue = new Queue(queueName, false, false, true);
                admin.declareQueue(dynamicQueue);
                admin.declareBinding(BindingBuilder.bind(dynamicQueue)
                        .to(context.getBean(TopicExchange.class))
                        .with(routingKey));

               // Setar fila dinamicamente no container
                container.setQueueNames(queueName);
                //container.setQueueNames(freshMarketQueue.getName()); // usa a fila já configurada
                container.start();

                System.out.println("\n✅ You chose:");
                System.out.println("Supermarket: " + selectedSupermarket);
                System.out.println("Sector: " + selectedSector);
                System.out.println("🎧 Listening to routing key: " + routingKey);
                break;

//                container.setQueues(selectedQueue);
//                container.start();
//
//                System.out.println("\n✅ You chose:");
//                System.out.println("Supermarket: " + selectedSupermarket);
//                System.out.println("Sector: " + selectedSector);
//                break;

            }
        };
    }
}