package com.monopoco.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.entity
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 12:50
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_export_import_detail")
public class ImportExportDetail extends AuditEntity<UUID> {

    @Id
    private UUID id;

    @Column(name = "type")
    @Comment("0: Import\n 1: Export")
    private Integer type;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "quantity")
    private Long quantity;
}
