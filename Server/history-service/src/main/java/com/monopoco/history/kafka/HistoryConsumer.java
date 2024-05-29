package com.monopoco.history.kafka;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.history.service.HistoryService;
import com.monopoco.history.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Project: Server
 * Package: com.monopoco.history.kafka
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 11:39
 */

@Service
public class HistoryConsumer {

    private static final Logger log = LoggerFactory.getLogger(HistoryConsumer.class);

    @Autowired
    private HistoryService historyService;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(HistoryEvent historyEvent) {
        log.info(String.format("History received in History Service => %s", historyEvent.toString()));
        //save History
        if (historyEvent.getContent() != null) {
            historyEvent.setContent(CommonUtil.EncodingFix(historyEvent.getContent()));
        }
        historyService.save(
                historyEvent
        );

    }
}
