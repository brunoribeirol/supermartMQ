package com.SupermartMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = "marketHubQueue")
    public void receiveMessage(byte[] message) {
        System.out.println("📥 Received: " + new String(message));
    }
}
