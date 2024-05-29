package com.monopoco.socket.controller;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.socket.kafka.SocketProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Project: Server
 * Package: com.monopoco.socket.controller
 * Author: hungdq
 * Date: 07/05/2024
 * Time: 20:27
 */

@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SocketProducer socketProducer;


    @MessageMapping("/message")
    private HistoryEvent receivePublicMessage(@Payload HistoryEvent historyEvent) {
        System.out.println(historyEvent.getAgentId().toString());
        simpMessagingTemplate.convertAndSendToUser(historyEvent.getAgentId().toString(), "/private", historyEvent);
        socketProducer.sendMessage(historyEvent);
        return historyEvent;
    }
}
