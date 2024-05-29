package com.monopoco.warehouse.repository;

import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.warehouse.area.AisleDTO;
import com.monopoco.common.model.warehouse.area.AreaWarehouseDTO;
import com.monopoco.warehouse.filter.WarehouseFilter;
import com.monopoco.warehouse.response.model.WarehouseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.repository.impl
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 13:04
 */
public interface AreaWarehouseRepositoryDSL {

    public List<AreaWarehouseDTO> areaWarehouse(UUID warehouseId);

    public List<AisleDTO> getAllAisleInArea(UUID areaId);
}