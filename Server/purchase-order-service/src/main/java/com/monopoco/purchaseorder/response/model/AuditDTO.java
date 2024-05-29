package com.monopoco.purchaseorder.response.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Project: Server
 * Package: com.monopoco.authservice.response.model
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:15
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuditDTO {

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;

    private Boolean isDeleted;
}
