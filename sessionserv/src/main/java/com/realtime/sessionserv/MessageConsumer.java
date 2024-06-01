package com.realtime.sessionserv;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @KafkaListener(topics = "messages", groupId = "messenger-group")
    public void listen(Message message) {
        // 处理消息逻辑
        System.out.println("Received message: " + message);
    }
}
