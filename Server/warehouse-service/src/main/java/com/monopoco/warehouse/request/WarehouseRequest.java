package com.monopoco.warehouse.request;

import com.monopoco.warehouse.response.model.DropDown;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.request
 * Author: hungdq
 * Date: 30/03/2024
 * Time: 00:22
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WarehouseRequest {

    private String warehouseName;

    private String warehouseNameAcronym;

    private String location;

    private String provinceId;

    private String provinceName;

    private String districtId;

    private String districtName;

    private String wardId;

    private String wardName;

    private DropDown<UUID, String> warehouseType;
}
