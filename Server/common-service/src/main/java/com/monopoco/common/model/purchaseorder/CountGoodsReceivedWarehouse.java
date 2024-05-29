package com.monopoco.common.model.purchaseorder;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.purchaseorder
 * Author: hungdq
 * Date: 16/05/2024
 * Time: 10:54
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CountGoodsReceivedWarehouse {

    UUID warehouseId;

    long total;

    long needHandle;
}
