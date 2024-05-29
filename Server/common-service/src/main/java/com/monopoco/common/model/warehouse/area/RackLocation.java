package com.monopoco.common.model.warehouse.area;

import jakarta.persistence.Column;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 10/05/2024
 * Time: 17:07
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RackLocation {

    private UUID rackId;

    private String rackName;

    List<LevelRackLocation> levelRackLocations;
}