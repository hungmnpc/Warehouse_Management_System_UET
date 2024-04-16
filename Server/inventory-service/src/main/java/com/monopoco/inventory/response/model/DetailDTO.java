package com.monopoco.inventory.response.model;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.response.model
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 16:32
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class DetailDTO extends AuditDTO{

    private UUID id;

    private String type;

    private UUID productId;

    private String productName;

    private UUID requestId;

    private Long quantity;

    @QueryProjection
    public DetailDTO(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, Boolean isDeleted, UUID id, String type, UUID productId, String productName, UUID requestId, Long quantity) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDeleted);
        this.id = id;
        this.type = type;
        this.productId = productId;
        this.productName = productName;
        this.requestId = requestId;
        this.quantity = quantity;
    }
}
