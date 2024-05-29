package com.monopoco.inventory.util;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.product.util
 * Author: hungdq
 * Date: 07/05/2024
 * Time: 14:42
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PrincipalUser {

    private String username;

    private UUID userId;

    private UUID warehouseId;
}
