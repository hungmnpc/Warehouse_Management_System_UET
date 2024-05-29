package com.monopoco.supplier.response.model;

import com.querydsl.core.annotations.QueryProjection;
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
@SuperBuilder
public class UserDTO extends AuditDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    private String userName;

    private String roleName;

    @QueryProjection
    public UserDTO(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, Boolean isDeleted, UUID id, String firstName, String lastName, String userName, String roleName) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate, isDeleted);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.roleName = roleName;
    }

    public UserDTO(UUID id, String firstName, String lastName, String userName, String roleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.roleName = roleName;
    }
}
