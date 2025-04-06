package com.SupermartMQ.audit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuditReceiver {

    @RabbitListener(queues = "audit.queue")
    public void receiveAudit(byte[] message) {
        String msg = new String(message);
        System.out.println("🕵 AUDIT received:");
        System.out.println(msg);
        System.out.println("----------------------------------------");
    }
}
