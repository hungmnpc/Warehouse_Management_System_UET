package com.monopoco.history.response.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String createdBy;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String lastModifiedBy;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastModifiedDate;

//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean isDeleted;
}
