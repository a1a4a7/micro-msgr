package com.realtime.msgserv;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        messageProducer.sendMessage(message);
    }
}
