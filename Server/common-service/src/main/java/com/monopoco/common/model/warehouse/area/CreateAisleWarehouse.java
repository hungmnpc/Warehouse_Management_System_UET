package com.monopoco.common.model.warehouse.area;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 16:56
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateAisleWarehouse {

    private String aisleName;

    private LocationType locationType;

    private CreateFloorStorage createFloorStorage;

    private CreateRackStorage createRackStorage;
}
