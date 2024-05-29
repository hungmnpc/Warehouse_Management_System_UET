package com.monopoco.musicmp4.Utils;

import java.util.UUID;

public class UserPrinciple {

    private String username;

    private UUID userId;

    private UUID warehouseId;

    public UserPrinciple(String username, UUID userId, UUID warehouseId) {
        this.username = username;
        this.userId = userId;
        this.warehouseId = warehouseId;
    }

    public UserPrinciple() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }
}
