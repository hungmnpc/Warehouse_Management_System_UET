package com.monopoco.common.model.warehouse.area;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 10:47
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateAreaGroup{

    private String areaGroupId;

    private String areaGroupName;
}
