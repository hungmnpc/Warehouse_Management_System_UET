package com.monopoco.supplier.repository;

import com.monopoco.supplier.entity.Supplier;
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
 * Time: 17:44
 */

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    Optional<Supplier> findByIsDeletedIsFalseAndId(UUID id);

    List<Supplier> findAllByIsDeletedIsFalse();

    Optional<Supplier> findByIsDeletedIsFalseAndSupplierName(String supplierName);
}