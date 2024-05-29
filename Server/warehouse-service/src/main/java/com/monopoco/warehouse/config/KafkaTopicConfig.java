package com.monopoco.warehouse.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.config
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 11:06
 */
@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    // spring bean for kafka topic
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName)
                .build();
    }
}
