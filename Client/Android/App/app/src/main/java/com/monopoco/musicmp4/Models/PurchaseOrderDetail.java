package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class PurchaseOrderDetail {

    @SerializedName("product")
    private ProductModel product;

    @SerializedName("purchaseOrderDetailId")
    private UUID purchaseOrderDetailId;

    @SerializedName("purchaseOrderId")
    private UUID purchaseOrderId;

    @SerializedName("quantity")
    private Integer quantity;

    @SerializedName("stockedQuantity")
    private Integer stockedQuantity;

    public PurchaseOrderDetail(ProductModel product, UUID purchaseOrderDetailId, UUID purchaseOrderId, Integer quantity, Integer stockedQuantity) {
        this.product = product;
        this.purchaseOrderDetailId = purchaseOrderDetailId;
        this.purchaseOrderId = purchaseOrderId;
        this.quantity = quantity;
        this.stockedQuantity = stockedQuantity;
    }

    public PurchaseOrderDetail() {
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public UUID getPurchaseOrderDetailId() {
        return purchaseOrderDetailId;
    }

    public void setPurchaseOrderDetailId(UUID purchaseOrderDetailId) {
        this.purchaseOrderDetailId = purchaseOrderDetailId;
    }

    public UUID getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(UUID purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStockedQuantity() {
        return stockedQuantity;
    }

    public void setStockedQuantity(Integer stockedQuantity) {
        this.stockedQuantity = stockedQuantity;
    }
}
