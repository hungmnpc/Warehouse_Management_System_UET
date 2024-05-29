package com.monopoco.common.model.warehouse.area;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 11:23
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateFloorStorage {

    private Integer numberOfFloorStorage;

    private String floorStoragePrefix = "F";

    private Integer numberOfBin;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean multipleProduct = true;
}
