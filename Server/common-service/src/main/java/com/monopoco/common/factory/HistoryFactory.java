package com.monopoco.common.factory;

import com.monopoco.common.model.HistoryEvent;
import com.monopoco.common.model.HistoryType;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.factory
 * Author: hungdq
 * Date: 07/05/2024
 * Time: 14:34
 */
public class HistoryFactory {

    public static HistoryEvent createHistoryEventPOST(
            UUID userId, String username, UUID agentId,
            String agentType, String content
    ) {
        return HistoryEvent.builder()
                .type(HistoryType.POST)
                .userId(userId)
                .username(username)
                .agentId(agentId)
                .agentType(agentType)
                .content(content)
                .ts(System.currentTimeMillis())
                .build();

    }

    public static HistoryEvent createHistoryEventGET(
            UUID userId, String username, UUID agentId,
            String agentType, String content
    ) {
        return HistoryEvent.builder()
                .type(HistoryType.GET)
                .userId(userId)
                .username(username)
                .agentId(agentId)
                .agentType(agentType)
                .content(content)
                .ts(System.currentTimeMillis())
                .build();

    }

    public static HistoryEvent createHistoryEventUPDATE(
            UUID userId, String username, UUID agentId,
            String agentType, String content
    ) {
        return HistoryEvent.builder()
                .type(HistoryType.UPDATE)
                .userId(userId)
                .username(username)
                .agentId(agentId)
                .agentType(agentType)
                .ts(System.currentTimeMillis())
                .content(content)
                .build();

    }

    public static HistoryEvent createHistoryEventDELETE(
            UUID userId, String username, UUID agentId,
            String agentType, String content
    ) {
        return HistoryEvent.builder()
                .type(HistoryType.DELETE)
                .userId(userId)
                .username(username)
                .agentId(agentId)
                .agentType(agentType)
                .content(content)
                .ts(System.currentTimeMillis())
                .build();

    }
}
