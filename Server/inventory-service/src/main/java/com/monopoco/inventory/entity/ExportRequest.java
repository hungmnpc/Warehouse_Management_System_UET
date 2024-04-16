package com.monopoco.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.entity
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 12:34
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "tb_export_requests")
public class ExportRequest extends AuditEntity<UUID>{
    @Id
    private UUID id;

    @Column(name = "warehouse_id")
    private UUID warehouseId;


    @Column(name = "export_request_code")
    private String exportRequestCode;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "employee_1")
    private UUID employeeId1;

    @Column(name = "employee_2")
    private UUID employeeId2;

    @Column(name = "employee_3")
    private UUID employeeId3;

    @Column(name = "employee_4")
    private UUID employeeId4;
}
