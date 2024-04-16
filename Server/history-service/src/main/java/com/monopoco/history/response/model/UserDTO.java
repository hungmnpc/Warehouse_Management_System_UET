package com.monopoco.history.response.model;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.authservice.response.model
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:15
 */

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {

    private UUID id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String firstName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String lastName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String roleName;

}
