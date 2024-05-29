package com.monopoco.common.model.inventory;

import jakarta.persistence.Column;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.inventory
 * Author: hungdq
 * Date: 17/05/2024
 * Time: 14:43
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductBinInventoryDTO {

    private UUID id;

    private UUID productId;

    private String productName;

    private String binName;

    private UUID binId;

    private Integer quantity;
}
