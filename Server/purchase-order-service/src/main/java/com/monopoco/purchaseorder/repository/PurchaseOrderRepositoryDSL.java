package com.monopoco.purchaseorder.repository;

import com.monopoco.common.model.PageResponse;
import com.monopoco.common.model.purchaseorder.PurchaseOrderDTO;
import com.monopoco.common.model.purchaseorder.PurchaseOrderDetailDTO;
import com.monopoco.purchaseorder.service.filter.ProductFilter;
import com.monopoco.purchaseorder.service.filter.SearchPurchaseOrder;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository
 * Author: hungdq
 * Date: 24/04/2024
 * Time: 16:48
 */
public interface PurchaseOrderRepositoryDSL {

    public PageResponse<List<PurchaseOrderDTO>> searchOrder(SearchPurchaseOrder filter, Pageable pageable);

    public PageResponse<List<PurchaseOrderDetailDTO>> searchOrderProductDTO(UUID purchaseOrderId, ProductFilter filter, Pageable pageable);

    public PageResponse<List<PurchaseOrderDTO>> searchPOForEmployee(
            UUID warehouseId, UUID employeeId
    );
}