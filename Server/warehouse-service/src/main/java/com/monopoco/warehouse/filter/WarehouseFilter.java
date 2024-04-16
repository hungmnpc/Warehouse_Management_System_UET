package com.monopoco.warehouse.filter;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.filter
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 18:44
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WarehouseFilter {

    private String warehouseName;

    private String orderBy;

    private String orderType;

}
