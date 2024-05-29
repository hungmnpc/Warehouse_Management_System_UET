package com.monopoco.purchaseorder.repository.filter;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository
 * Author: hungdq
 * Date: 24/04/2024
 * Time: 16:48
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PurchaseOrderFilter {

    private String poCode;

    private String referenceNumber;
}
