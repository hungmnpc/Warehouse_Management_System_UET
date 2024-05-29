package com.monopoco.common.model.warehouse.area;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 10:21
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateArea {

    private String areaName;

    private String areaPrefix;

    private UUID areaGroupId;

    private String areaGroupName;
}
