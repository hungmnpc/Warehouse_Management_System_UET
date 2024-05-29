package com.monopoco.purchaseorder.service;

import com.monopoco.common.model.CommonResponse;
import com.monopoco.common.model.purchaseorder.AssignedUser;
import com.monopoco.common.model.purchaseorder.Status;
import com.monopoco.purchaseorder.service.filter.ProductFilter;
import com.monopoco.purchaseorder.service.filter.SearchPurchaseOrder;
import com.monopoco.purchaseorder.service.request.ProductPurchaseOrderRequest;
import com.monopoco.purchaseorder.service.request.PurchaseOrderRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository.service
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 16:59
 */
public interface PurchaseOrderService {

    CommonResponse<?> createNewPurchaseOrder(PurchaseOrderRequest request);

    CommonResponse<?> addProductToPurchaseOrder(ProductPurchaseOrderRequest request, UUID purchaseOrderId);

    CommonResponse<?> getAllPurchaseOrders(SearchPurchaseOrder filter, Pageable pageable);

    CommonResponse<?> getPurchaseOrderById(UUID id);

    CommonResponse<?> deleteAPurchaseOrder(UUID id);

    CommonResponse<?> updateQuantityOfPurchaseOrderDetail(UUID purchaseOrderDetailId, Integer quantity);

    CommonResponse<?> changeStatus(Status status, UUID id);

    CommonResponse<?> getAllItemInPurchaseOrder(UUID purchaseOrderId, Pageable pageable, ProductFilter productFilter);

    CommonResponse<?> deletePurchaseOrderDetail(UUID id);

    CommonResponse<?> assignedPOForUser(AssignedUser assignedUser);

    CommonResponse<?> checkIsAssignedPO(UUID poId);

    CommonResponse<?> changeDeadlineToStock(UUID poId, LocalDate deadline);

    CommonResponse<?> getAllPOInAssignedToEmployee();

    CommonResponse<?> getPurchaseOrderDetailByPoIdAndProductId(
            UUID poId, UUID productId
    );
    CommonResponse<?> stockPurchaseOrder(UUID employeeID, String employeeName, UUID poId, UUID productId, Integer quantity);

}