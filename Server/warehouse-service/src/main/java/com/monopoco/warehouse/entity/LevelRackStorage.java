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
 * Time: 10:09
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_level_rack_storages")
@Entity
public class LevelRackStorage {

    @Id
    @Column(name = "level_rack_id")
    private UUID levelRackId;

    @Column(name = "rack_storage_id")
    private UUID rackStorageId;

    @Column(name = "level_name")
    private String levelName;

    private Integer level;
}

