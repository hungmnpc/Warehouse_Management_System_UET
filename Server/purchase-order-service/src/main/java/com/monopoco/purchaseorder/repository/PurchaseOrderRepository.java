package com.monopoco.purchaseorder.repository;

import com.monopoco.purchaseorder.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 16:57
 */

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {

    Optional<PurchaseOrder> findByIsDeletedIsFalseAndId(UUID id);

    Optional<PurchaseOrder> findByIsDeletedIsFalseAndPoCode(String poCode);



    long countAllByIsDeletedIsFalse();
}