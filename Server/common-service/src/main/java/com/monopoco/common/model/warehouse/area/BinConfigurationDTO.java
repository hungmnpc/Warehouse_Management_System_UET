package com.monopoco.common.model.warehouse.area;

import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 12/05/2024
 * Time: 00:28
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BinConfigurationDTO {

    private String binDescription;

    private Integer maxStorage;

    private String unitStorage;

    private UUID onlyProductId;

    private String onlyProductName;
}
