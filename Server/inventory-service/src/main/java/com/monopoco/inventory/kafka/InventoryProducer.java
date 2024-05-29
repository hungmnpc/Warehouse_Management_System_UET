package com.monopoco.inventory.kafka;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.common.model.inventory.PickEvent;
import com.monopoco.common.model.inventory.StockEvent;
import com.monopoco.inventory.clients.HistoryDTO;
import com.monopoco.inventory.util.CommonUtil;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class InventoryProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryProducer.class);


    @Qualifier("historyTopic")
    private NewTopic historyTopic;

    @Qualifier("stockTopic")
    private NewTopic stockTopic;

    @Autowired
    private KafkaTemplate<String, HistoryEvent> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, StockEvent> kafkaTemplateStock;

    @Autowired
    private KafkaTemplate<String, PickEvent> kafkaTemplatePick;



    public void sendMessage(HistoryEvent historyEvent) {
        LOGGER.info(String.format("Purchase Order Event => %s", historyEvent.toString()));

        // Create message
        Message<HistoryEvent> message = MessageBuilder
                .withPayload(historyEvent)
                .setHeader(KafkaHeaders.TOPIC, "history")
                .build();
        kafkaTemplate.send(message);
    }

    public void sendStockMessage(StockEvent stockEvent) {
        LOGGER.info(String.format("Event Received => %s", stockEvent.toString()));

        // Create message
        Message<StockEvent> message = MessageBuilder
                .withPayload(stockEvent)
                        .setHeader(KafkaHeaders.TOPIC, "stock")
                                .build();

        kafkaTemplateStock.send(message);
    }

    public void sendPickMessage(PickEvent pickEvent) {
        LOGGER.info(String.format("Event Send => %s", pickEvent.toString()));

        // Create message
        Message<PickEvent> message = MessageBuilder
                .withPayload(pickEvent)
                .setHeader(KafkaHeaders.TOPIC, "pick")
                .build();

        kafkaTemplatePick.send(message);
    }

}
