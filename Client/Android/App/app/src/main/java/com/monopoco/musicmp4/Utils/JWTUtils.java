package com.monopoco.musicmp4.Utils;

import com.auth0.android.jwt.JWT;

import java.util.UUID;

public class JWTUtils {

    public static UserPrinciple getCurrentUser(String token) {
        JWT jwt = new JWT(token);

        String username = jwt.getSubject(); //get registered claims
        UUID userId = jwt.getClaim("id").asObject(UUID.class);
        UUID warehouseId = jwt.getClaim("warehouseId").asObject(UUID.class);
        return new UserPrinciple(
                username, userId, warehouseId
        );
    }
}
