package com.monopoco.supplier.repository;

import com.monopoco.supplier.entity.SupplierProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.supplier.repository
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:45
 */
@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, UUID> {

    Optional<SupplierProduct> findByIsDeletedIsFalseAndId(UUID id);

    Optional<SupplierProduct> findByIsDeletedIsFalseAndProductIdAndSupplierId(UUID productId, UUID supplierId);

    Page<SupplierProduct> findAllByIsDeletedIsFalseAndSupplierIdOrderByCreatedDateDesc(UUID supplierId, Pageable pageable);
}