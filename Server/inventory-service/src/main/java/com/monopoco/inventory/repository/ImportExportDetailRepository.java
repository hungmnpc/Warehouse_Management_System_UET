package com.monopoco.inventory.repository;

import com.monopoco.inventory.entity.ImportExportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.repository
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 12:59
 */

@Repository
public interface ImportExportDetailRepository extends JpaRepository<ImportExportDetail, UUID> {

    Optional<ImportExportDetail> findByIsDeletedIsFalseAndProductIdAndRequestId(UUID productId, UUID requestId);
}
