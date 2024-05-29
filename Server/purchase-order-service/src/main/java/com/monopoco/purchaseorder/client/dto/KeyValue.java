package com.monopoco.purchaseorder.client.dto;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.client.dto
 * Author: hungdq
 * Date: 25/04/2024
 * Time: 23:19
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class KeyValue {

    private String key;

    private String value;
}
