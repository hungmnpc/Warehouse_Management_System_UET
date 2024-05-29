package com.monopoco.purchaseorder.service.request;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.service.request
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 16:10
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductPurchaseOrderRequest {

    private UUID productId;

    private Long quantity;
}
