package com.monopoco.common.model.purchaseorder;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.purchaseorder
 * Author: hungdq
 * Date: 08/05/2024
 * Time: 23:49
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CountPurchaseOrderWarehouse {

    private UUID warehouseId;

    private long countAllPurchaseOrder;

    private long countDraftPurchaseOrder;
}
