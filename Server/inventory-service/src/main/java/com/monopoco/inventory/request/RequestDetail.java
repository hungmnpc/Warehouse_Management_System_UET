package com.monopoco.inventory.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.request
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 16:14
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RequestDetail {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer type;

    private UUID productId;

    private String productName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID requestId;

    private Long quantity;
}
