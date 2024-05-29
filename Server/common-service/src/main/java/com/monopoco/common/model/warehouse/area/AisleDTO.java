package com.monopoco.common.model.warehouse.area;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 17:58
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class AisleDTO {

    private UUID aisleId;

    private String aisleName;

    private LocationType locationType;

    @QueryProjection
    public AisleDTO(UUID aisleId, String aisleName, LocationType locationType) {
        this.aisleId = aisleId;
        this.aisleName = aisleName;
        this.locationType = locationType;
    }
}
