package com.monopoco.purchaseorder.service.filter;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository.service.filter
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:02
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SearchPurchaseOrder {

    private String poCode;

    private List<Integer> status;

    private String referenceNumber;

    private LocalDate arrivalDateFrom;

    private LocalDate arrivalDateTo;

    private UUID warehouseId;
}
