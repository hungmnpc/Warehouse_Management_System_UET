package com.monopoco.common.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model
 * Author: hungdq
 * Date: 06/05/2024
 * Time: 17:59
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HistoryEvent {

    private long ts = System.currentTimeMillis();

    private HistoryType type;

    private String username;

    private UUID userId;

    private String content;

    private String agentType;

    private UUID agentId;
}
