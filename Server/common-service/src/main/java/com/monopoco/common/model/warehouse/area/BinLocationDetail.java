package com.monopoco.common.model.warehouse.area;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 11/05/2024
 * Time: 20:29
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BinLocationDetail {

    private AreaWarehouseDTO area;

    private AisleDTO aisle;

    private RackLocation rack;

    private LevelRackLocation levelRackLocation;

    private BinLocation binLocation;

    private BinConfigurationDTO binConfigurationDTO;
}
