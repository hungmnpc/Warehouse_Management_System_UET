package com.monopoco.warehouse.entity;

import com.monopoco.common.model.warehouse.area.BinConfigurationDTO;
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
 * Date: 12/05/2024
 * Time: 00:04
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tb_bin_configs")
@Entity
public class BinConfiguration {

    @Id
    private UUID id;

    @Column(name = "bin_id")
    private UUID binId;

    @Column(name = "bin_description", columnDefinition = "TEXT")
    private String binDescription;

    @Column(name = "max_storage")
    private Integer maxStorage;

    @Column(name = "unit_storage")
    private String unitStorage;

    @Column(name = "only_product_id")
    private UUID onlyProductId;

    @Column(name = "only_product_name")
    private String onlyProductName;

    public void updateFromDTO(BinConfigurationDTO dto) {
        this.binDescription = dto.getBinDescription();
        this.maxStorage = dto.getMaxStorage();
        this.onlyProductId = dto.getOnlyProductId();
        this.onlyProductName = dto.getOnlyProductName();
        this.unitStorage = dto.getUnitStorage();
    }
}
