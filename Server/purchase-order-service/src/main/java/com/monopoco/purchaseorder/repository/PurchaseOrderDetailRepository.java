package com.monopoco.purchaseorder.repository;

import com.monopoco.purchaseorder.entity.PurchaseOrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 16:58
 */

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, UUID> {

    Optional<PurchaseOrderDetail> findByIsDeletedIsFalseAndId(UUID id);

    Optional<PurchaseOrderDetail> findByIsDeletedIsFalseAndProductIdAndPurchaseOrderId(UUID productId, UUID purchaseOrderId);

    List<PurchaseOrderDetail> findAllByIsDeletedIsFalseAndPurchaseOrderId(UUID purchaseOrderId);

    Integer countAllByIsDeletedIsFalseAndPurchaseOrderId(UUID purchaseOrderId);
}
