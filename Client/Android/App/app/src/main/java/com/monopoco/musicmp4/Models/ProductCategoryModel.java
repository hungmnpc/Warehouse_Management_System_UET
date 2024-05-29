package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

public class ProductCategoryModel {

    @SerializedName("categoryName")
    private String categoryName;

    public ProductCategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public ProductCategoryModel() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
