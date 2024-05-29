package com.monopoco.inventory.request;

import com.monopoco.inventory.entity.Status;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.request
 * Author: hungdq
 * Date: 05/05/2024
 * Time: 17:04
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ImportRequestBodyPo {

    private String importRequestCode;

    private LocalDate deliveryDate;

    private LocalDate estimatedArrivalDate;

    private UUID employeeId1;

    private UUID employeeId2;

    private UUID employeeId3;

    private UUID employeeId4;
}
