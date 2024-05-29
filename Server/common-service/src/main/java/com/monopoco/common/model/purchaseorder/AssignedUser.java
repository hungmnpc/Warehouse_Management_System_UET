package com.monopoco.common.model.purchaseorder;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.purchaseorder
 * Author: hungdq
 * Date: 14/05/2024
 * Time: 23:30
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AssignedUser {

    private UUID poId;

    private UUID userId;
}
