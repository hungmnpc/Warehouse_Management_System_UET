package com.monopoco.inventory.repository;

import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.ProductInventoryDTO;
import com.monopoco.inventory.filter.ImportRequestFilter;
import com.monopoco.inventory.response.model.ImportRequestDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.repository
 * Author: hungdq
 * Date: 14/05/2024
 * Time: 18:13
 */
public interface ProductInventoryRepositoryDSL {

    public PageResponse<List<ProductInventoryDTO>> searchOrder(String productName, UUID warehouseId, Pageable pageable);
}