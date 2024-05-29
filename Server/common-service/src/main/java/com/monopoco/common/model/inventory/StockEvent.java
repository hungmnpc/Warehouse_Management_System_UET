package com.monopoco.common.model.inventory;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.inventory
 * Author: hungdq
 * Date: 16/05/2024
 * Time: 22:53
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StockEvent {

    private UUID userId;

    private String username;

    private UUID purchaseOrderId;

    private UUID productId;

    private Integer quantity;

    private UUID binId;
}
