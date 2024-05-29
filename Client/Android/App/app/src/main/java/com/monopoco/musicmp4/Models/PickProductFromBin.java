package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

public class PickProductFromBin {


    @SerializedName("binBarcode")
    private String binBarcode;

    @SerializedName("productBarcode")
    private String productBarcode;

    @SerializedName("quantity")
    private Integer quantity;

    public PickProductFromBin(String binBarcode, String productBarcode, Integer quantity) {
        this.binBarcode = binBarcode;
        this.productBarcode = productBarcode;
        this.quantity = quantity;
    }

    public PickProductFromBin() {
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
}
