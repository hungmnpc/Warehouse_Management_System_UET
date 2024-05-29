package com.monopoco.supplier.response.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.authservice.response.model
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:14
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoleDTO extends AuditDTO {

    private UUID id;

    private String roleName;
}
