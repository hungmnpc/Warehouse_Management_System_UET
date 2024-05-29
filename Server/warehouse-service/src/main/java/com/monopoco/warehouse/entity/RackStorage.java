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
 * Time: 10:00
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_racks_storage")
@Entity
public class RackStorage {

    @Id
    @Column(name = "rack_id")
    private UUID rackId;

    @Column(name = "rack_name")
    private String rackName;

    @Column(name = "aisle_id")
    private UUID aisleId;
}
