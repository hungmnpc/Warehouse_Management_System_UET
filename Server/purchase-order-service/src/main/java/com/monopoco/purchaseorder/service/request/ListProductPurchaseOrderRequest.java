package com.monopoco.purchaseorder.service.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.service.request
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 16:11
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ListProductPurchaseOrderRequest {

    private List<ProductPurchaseOrderRequest> products;

    private UUID purchaseOrderId;

}
