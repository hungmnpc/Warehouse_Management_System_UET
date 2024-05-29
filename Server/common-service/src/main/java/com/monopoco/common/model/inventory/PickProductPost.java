package com.monopoco.common.model.inventory;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.inventory
 * Author: hungdq
 * Date: 17/05/2024
 * Time: 17:39
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PickProductPost {

    private String binBarcode;

    private String productBarcode;

    private Integer quantity;
}
