package com.monopoco.history.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monopoco.common.model.HistoryType;
import com.monopoco.history.response.model.KeyValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Map;

/**
 * Project: Server
 * Package: com.monopoco.history.entity
 * Author: hungdq
 * Date: 04/04/2024
 * Time: 16:05
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "WMS_History")
public class History {

    @MongoId
    private ObjectId id;

    @Field("userId")
    @JsonProperty("userId")
    private String userId;

    @Field("username")
    private String username;

    @Field(name = "ts")
    private Long ts;

    @Field(name = "type")
    @Enumerated(EnumType.STRING)
    private HistoryType type;

    @Field(name = "content")
    private String content;

    @Field(name = "agent_type")
    @JsonProperty("agent_type")
    private String agentType;

    @Field(name = "agent_id")
    @JsonProperty("agent_id")
    private String agentId;
}
