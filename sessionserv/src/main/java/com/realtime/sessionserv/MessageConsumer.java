package com.realtime.sessionserv;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.realtime.sessionserv.Message;

@Service
public class MessageConsumer {

    private final SessionService sessionService;

    public MessageConsumer(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @KafkaListener(topics = "messages", groupId = "messenger-group")
    public void listen(Message message) {
        // 处理收到的消息，这里可以更新会话或执行其他操作
        sessionService.updateSession(message);
    }
}
