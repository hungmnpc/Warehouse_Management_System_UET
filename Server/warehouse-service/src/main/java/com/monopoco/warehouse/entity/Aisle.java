package com.monopoco.warehouse.entity;

import com.monopoco.common.model.warehouse.area.LocationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.entity
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 16:45
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_aisles")
@Entity
public class Aisle {

    @Id
    @Column(name = "aisle_id")
    private UUID aisleId;

    @Column(name = "aisle_name")
    private String aisleName;

    @Column(name = "location_type")
    @Enumerated(EnumType.STRING)
    private LocationType locationType;

    @Column(name = "area_id")
    private UUID areaId;
}
