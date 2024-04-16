package com.monopoco.warehouse.clients.dto;

import com.monopoco.warehouse.response.model.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.clients.dto
 * Author: hungdq
 * Date: 15/04/2024
 * Time: 17:40
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HistoryDTO{


    private Long ts;

    private String type;

    private String title;

    private UserDTO user;

    private String agentType;

    private UUID agentId;

    private Map<String, Object> description;
}
