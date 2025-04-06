package com.SupermartMQ.consumer;

import org.springframework.stereotype.Component;

@Component
public class ConsumerReceiver {

    public void receiveMessage(byte[] message) {
        String msg = new String(message);
        System.out.println("📥 CONSUMER received:");
        System.out.println(msg);
        System.out.println("----------------------------------------");
    }
}