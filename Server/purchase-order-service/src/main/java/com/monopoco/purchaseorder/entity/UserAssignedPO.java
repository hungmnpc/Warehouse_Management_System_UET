package com.monopoco.purchaseorder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.entity
 * Author: hungdq
 * Date: 14/05/2024
 * Time: 23:07
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_user_assigned_po")
@Entity
public class UserAssignedPO {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "po_id")
    private UUID poId;
}
