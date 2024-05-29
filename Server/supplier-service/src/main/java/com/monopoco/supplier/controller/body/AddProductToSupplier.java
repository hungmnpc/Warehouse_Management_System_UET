package com.monopoco.supplier.controller.body;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.supplier.controller.body
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 21:05
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AddProductToSupplier {

    private UUID productId;

    private UUID supplierId;
}
