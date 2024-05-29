package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class ProductModel {

    @SerializedName("productId")
    private UUID productId;

    @SerializedName("productCode")
    private String productCode;

    @SerializedName("productName")
    private String productName;

    @SerializedName("sku")
    private String sku;

    @SerializedName("productDescription")
    private String productDescription;

    @SerializedName("productCategoryDTO")
    private ProductCategoryModel productCategoryDTO;

    @SerializedName("reorderQuantity")
    private Integer reorderQuantity;

    @SerializedName("unit")
    private String unit;

    @SerializedName("isPacked")
    private Boolean isPacked;

    @SerializedName("packedWeight")
    private Integer packedWeight;

    @SerializedName("packedHeight")
    private Integer packedHeight;

    @SerializedName("packedWidth")
    private Integer packedWidth;

    @SerializedName("packedDepth")
    private Integer packedDepth;

    @SerializedName("barcode")
    private String barcode;

    public ProductModel(UUID productId, String productCode, String productName, String sku, String productDescription, ProductCategoryModel productCategoryDTO, Integer reorderQuantity, String unit, Boolean isPacked, Integer packedWeight, Integer packedHeight, Integer packedWidth, Integer packedDepth, String barcode) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.sku = sku;
        this.productDescription = productDescription;
        this.productCategoryDTO = productCategoryDTO;
        this.reorderQuantity = reorderQuantity;
        this.unit = unit;
        this.isPacked = isPacked;
        this.packedWeight = packedWeight;
        this.packedHeight = packedHeight;
        this.packedWidth = packedWidth;
        this.packedDepth = packedDepth;
        this.barcode = barcode;
    }

    public ProductModel() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public ProductCategoryModel getProductCategoryDTO() {
        return productCategoryDTO;
    }

    public void setProductCategoryDTO(ProductCategoryModel productCategoryDTO) {
        this.productCategoryDTO = productCategoryDTO;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getPacked() {
        return isPacked;
    }

    public void setPacked(Boolean packed) {
        isPacked = packed;
    }

    public Integer getPackedWeight() {
        return packedWeight;
    }

    public void setPackedWeight(Integer packedWeight) {
        this.packedWeight = packedWeight;
    }

    public Integer getPackedHeight() {
        return packedHeight;
    }

    public void setPackedHeight(Integer packedHeight) {
        this.packedHeight = packedHeight;
    }

    public Integer getPackedWidth() {
        return packedWidth;
    }

    public void setPackedWidth(Integer packedWidth) {
        this.packedWidth = packedWidth;
    }

    public Integer getPackedDepth() {
        return packedDepth;
    }

    public void setPackedDepth(Integer packedDepth) {
        this.packedDepth = packedDepth;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
