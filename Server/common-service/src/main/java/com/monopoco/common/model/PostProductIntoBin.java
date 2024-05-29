package com.monopoco.common.model;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model
 * Author: hungdq
 * Date: 13/05/2024
 * Time: 10:27
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PostProductIntoBin {

    private UUID poId;

    private String binBarcode;

    private String productBarcode;

    private Integer quantity;
}
