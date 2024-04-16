package com.monopoco.warehouse.response.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.response.model
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 00:57
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
public class WarehouseTypeDTO extends AuditDTO {

    private UUID id;

    private String warehouseTypeName;

    private String description;
}
