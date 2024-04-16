package com.monopoco.history.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;
import java.util.UUID;

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

    @Field(name = "ts")
    private Long ts;

    @Field(name = "type")
    private String type;

    @Field(name = "title")
    private String title;

    @Field(name = "agent_type")
    @JsonProperty("agent_type")
    private String agentType;

    @Field(name = "agent_id")
    @JsonProperty("agent_id")
    private String agentId;

    @Field(name = "description")
    private Map<String, Object> description;
}
