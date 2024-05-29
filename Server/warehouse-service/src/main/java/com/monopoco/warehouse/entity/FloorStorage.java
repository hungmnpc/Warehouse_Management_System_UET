package com.monopoco.warehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.entity
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 10:04
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_floors_storage")
@Entity
public class FloorStorage {

    @Id
    @Column(name = "floor_id")
    private UUID floorId;

    @Column(name = "floor_name")
    private String floorName;

    @Column(name = "aisle_id")
    private UUID aisleId;
}
