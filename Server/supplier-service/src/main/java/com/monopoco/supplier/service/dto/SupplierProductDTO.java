package com.monopoco.supplier.service.dto;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.supplier.service.dto
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:50
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SupplierProductDTO {

    private UUID id;

    private UUID supplierId;

    private UUID productId;
}
