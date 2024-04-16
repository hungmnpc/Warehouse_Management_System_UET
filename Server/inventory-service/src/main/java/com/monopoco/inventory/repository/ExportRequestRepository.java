package com.monopoco.inventory.repository;

import com.monopoco.inventory.entity.ExportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.repository
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 15:50
 */
public interface ExportRequestRepository extends JpaRepository<ExportRequest, UUID> {

    Optional<ExportRequest> findByIsDeletedIsFalseAndId(UUID id);
}