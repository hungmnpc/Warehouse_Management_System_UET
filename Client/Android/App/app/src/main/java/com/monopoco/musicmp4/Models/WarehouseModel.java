package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class WarehouseModel {

    @SerializedName("id")
    private UUID id;

    @SerializedName("warehouseName")
    private String warehouseName;

    @SerializedName("warehouseNameAcronym")
    private String warehouseNameAcronym;

    public WarehouseModel(UUID id, String warehouseName, String warehouseNameAcronym) {
        this.id = id;
        this.warehouseName = warehouseName;
        this.warehouseNameAcronym = warehouseNameAcronym;
    }

    public WarehouseModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseNameAcronym() {
        return warehouseNameAcronym;
    }

    public void setWarehouseNameAcronym(String warehouseNameAcronym) {
        this.warehouseNameAcronym = warehouseNameAcronym;
    }
}
