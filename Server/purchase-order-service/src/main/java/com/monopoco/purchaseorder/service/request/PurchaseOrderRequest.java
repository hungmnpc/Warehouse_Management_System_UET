package com.monopoco.purchaseorder.service.request;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.service.request
 * Author: hungdq
 * Date: 24/04/2024
 * Time: 15:53
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PurchaseOrderRequest {

    private String poCode;

    private String referenceNumber;

    private LocalDate inboundDate;

    private LocalDate arrivalDate;

    private String comment;

    private UUID supplierId;

    private UUID warehouseId;
}
