package com.monopoco.authservice.filter;

import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.authservice.filter
 * Author: hungdq
 * Date: 25/03/2024
 * Time: 18:04
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserFilter {

    String username;

    UUID warehouseId;

    String role;
}
