package com.monopoco.warehouse.request;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.request
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 00:49
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WarehouseTypeRequest {

    private String warehouseTypeName;

    private String description;
}
