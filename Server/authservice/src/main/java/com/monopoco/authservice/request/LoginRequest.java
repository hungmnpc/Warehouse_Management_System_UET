package com.monopoco.authservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.authservice.request
 * Author: hungdq
 * Date: 22/03/2024
 * Time: 17:54
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
