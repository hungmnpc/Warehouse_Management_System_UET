package com.monopoco.common.model.warehouse.area;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 13:02
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class AreaWarehouseDTO {

    private UUID id;

    private String areaName;

    private UUID warehouseId;

    private String warehouseName;

    private UUID areaGroupId;

    private String areaGroupName;

    private String areaPrefix;

    @QueryProjection
    public AreaWarehouseDTO(UUID id, String areaName, UUID warehouseId, String warehouseName, UUID areaGroupId, String areaGroupName, String areaPrefix) {
        this.id = id;
        this.areaName = areaName;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.areaGroupId = areaGroupId;
        this.areaGroupName = areaGroupName;
        this.areaPrefix = areaPrefix;
    }
}
