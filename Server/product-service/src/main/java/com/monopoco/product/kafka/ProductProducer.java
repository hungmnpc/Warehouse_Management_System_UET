package com.monopoco.product.kafka;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.product.util.CommonUtil;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.kafka
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 11:09
 */
@Service
public class ProductProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, HistoryEvent> kafkaTemplate;

    public ProductProducer(NewTopic topic, KafkaTemplate<String, HistoryEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendMessage(HistoryEvent historyEvent) {
        LOGGER.info(String.format("Purchase Order Event => %s", historyEvent.toString()));

        // Create message
        Message<HistoryEvent> message = MessageBuilder
                .withPayload(historyEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(topic.name(), CommonUtil.generateRandomUUID().toString(), historyEvent);
    }
}
