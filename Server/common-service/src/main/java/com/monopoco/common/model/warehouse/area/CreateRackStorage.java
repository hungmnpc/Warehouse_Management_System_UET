package com.monopoco.common.model.warehouse.area;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 11:20
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateRackStorage {

    private Integer numberOfRack;

    private String rackPrefix = "R";

    private Integer numberOfLevelEachRack;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean multipleProduct = true;

    private Integer numberOfBinEachLevel;
}
