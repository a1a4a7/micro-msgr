package com.realtime.msgserv;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaConfig {

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name("messages")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
