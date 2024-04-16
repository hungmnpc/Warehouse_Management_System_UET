package com.monopoco.history.response.model;


import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.util.Map;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.history.response.model
 * Author: hungdq
 * Date: 04/04/2024
 * Time: 16:32
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HistoryDTO{

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private ObjectId id;

    private Long ts;

    private String type;

    private String title;

    private UserDTO user;

    private String agentType;

    private UUID agentId;

    private Map<String, Object> description;
}
