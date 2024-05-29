package com.monopoco.common.model.warehouse.area;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 10/05/2024
 * Time: 16:40
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BinLocation {

    private UUID binId;

    private String binName;

    private Boolean disable = false;

    private Boolean occupied = false;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean isMultipleProduct = true;

    private String barcode;
}
