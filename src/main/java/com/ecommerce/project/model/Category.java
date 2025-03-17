package com.ecommerce.project.model;

public class Category {

    private long categoryId;
    private String categoryName;

    public Category(String categoryName, long categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryId(long categoryId) {

        this.categoryId = categoryId;
    }
}
