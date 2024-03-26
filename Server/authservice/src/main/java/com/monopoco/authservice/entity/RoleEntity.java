package com.monopoco.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.authservice.entity
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 16:29
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles")
@Entity
public class RoleEntity extends AuditEntity<UUID> {

   @Id
   private UUID id;

    @Column(name = "role_name", unique = true)
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<UserEntity> users;
}
