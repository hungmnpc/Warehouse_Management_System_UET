package com.monopoco.purchaseorder.repository;

import com.monopoco.purchaseorder.entity.UserAssignedPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.repository
 * Author: hungdq
 * Date: 14/05/2024
 * Time: 23:27
 */

@Repository
public interface UserAssignedPORepository extends JpaRepository<UserAssignedPO, UUID> {

    Optional<UserAssignedPO> findByPoId(UUID poId);

    Boolean existsByPoId(UUID poId);

    Optional<UserAssignedPO> findByPoIdAndUserId(UUID poId, UUID userId);
}
