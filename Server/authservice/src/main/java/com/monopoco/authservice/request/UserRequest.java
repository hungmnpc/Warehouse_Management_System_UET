package com.monopoco.authservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.authservice.request
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:18
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role_id")
    private UUID roleId;
}
