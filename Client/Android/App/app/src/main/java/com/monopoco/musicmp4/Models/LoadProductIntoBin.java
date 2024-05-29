package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class LoadProductIntoBin {

    @SerializedName("poId")
    private UUID poId;

    @SerializedName("binBarcode")
    private String binBarcode;

    @SerializedName("productBarcode")
    private String productBarcode;

    @SerializedName("quantity")
    private Integer quantity;

    public LoadProductIntoBin(UUID poId, String binBarcode, String productBarcode, Integer quantity) {
        this.poId = poId;
        this.binBarcode = binBarcode;
        this.productBarcode = productBarcode;
        this.quantity = quantity;
    }

    public LoadProductIntoBin() {
    }

    public String getBinBarcode() {
        return binBarcode;
    }

    public void setBinBarcode(String binBarcode) {
        this.binBarcode = binBarcode;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getPoId() {
        return poId;
    }

    public void setPoId(UUID poId) {
        this.poId = poId;
    }
}
