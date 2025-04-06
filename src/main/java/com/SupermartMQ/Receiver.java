package com.SupermartMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

//    @RabbitListener(queues = {
//            RabbitMQConfig.MARKET_HUB_QUEUE,
//            RabbitMQConfig.FRESH_MARKET_QUEUE,
//            RabbitMQConfig.MARKET_HUB_FRESH_MARKET_QUEUE
//    })
//    public void receiveMessage(byte[] message, @Header("amqp_consumerQueue") String queueName) {
//        String msg = new String(message);
//        System.out.println("📥 Received a message from: " + queueName);
//        System.out.println("🛒 Discount Info: " + msg);
//        System.out.println("----------------------------------------");
//    }

    public void receiveMessage(byte[] message) {
        String msg = new String(message);
        System.out.println("📥 Mensagem recebida:");
        System.out.println(msg);
        System.out.println("----------------------------------------");
    }

}