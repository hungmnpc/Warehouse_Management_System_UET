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
 * Time: 09:26
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_area_groups")
@Entity
public class AreaGroup {

    @Id
    private UUID id;

    @Column(name = "area_group_id")
    private String areaGroupId;

    @Column(name = "area_group_name")
    private String areaGroupName;
}
