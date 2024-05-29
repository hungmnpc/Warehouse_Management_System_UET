package com.monopoco.inventory.filter;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.filter
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 17:16
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ImportRequestFilter {

    private String warehouseName;

    private UUID warehouseId;

    private String orderBy;

    private String orderType;
}
