package com.monopoco.socket.kafka;

import com.monopoco.common.factory.CommonUtil;
import com.monopoco.common.model.HistoryEvent;
import com.monopoco.common.model.HistoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Project: Server
 * Package: com.monopoco.history.kafka
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 11:39
 */

@Service
public class SocketConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger log = LoggerFactory.getLogger(SocketConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HistoryEvent historyEvent) {
        log.info(String.format("History received in History Service => %s", historyEvent.toString()));
        if (historyEvent.getContent() != null) {
            historyEvent.setContent(CommonUtil.EncodingFix(historyEvent.getContent()));
        }
        //save History
        if (historyEvent != null && historyEvent.getType() != HistoryType.MESSAGE) {
            simpMessagingTemplate.convertAndSendToUser(historyEvent.getAgentId().toString(), "/private", historyEvent);
        }
    }
}
