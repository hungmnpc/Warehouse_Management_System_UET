package com.monopoco.inventory.repository;

import com.monopoco.inventory.entity.ImportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImportRequestRepository extends JpaRepository<ImportRequest, UUID> {

    Optional<ImportRequest> findByIsDeletedIsFalseAndId(UUID id);

    Optional<ImportRequest> findByIsDeletedIsFalseAndImportRequestCode(String importRequestCode);
}
