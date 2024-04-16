package com.monopoco.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "users")
@Entity
public class UserEntity extends AuditEntity<UUID>{

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "user_name", nullable = false, length = 100, unique = true)
    private String userName;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "password_salt", nullable = false, length = 256)
    private String passwordSalt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(name = "warehouse_id")
    private UUID warehouseId;
}
