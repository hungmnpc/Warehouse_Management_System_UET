package com.monopoco.product.controller.body;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.product.controller
 * Author: hungdq
 * Date: 23/04/2024
 * Time: 18:13
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BodyGetProduct {

    private List<UUID> notInIds;
}
