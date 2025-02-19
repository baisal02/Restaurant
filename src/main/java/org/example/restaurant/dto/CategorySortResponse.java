package org.example.restaurant.dto;

import java.util.List;

public class CategorySortResponse {
    private String categoryName;
    private List<SubCategoryResponse> subCategories;



    public CategorySortResponse(String categoryName, List<SubCategoryResponse> subCategories) {
        this.categoryName = categoryName;
        this.subCategories = subCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



    public List<SubCategoryResponse> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoryResponse> subCategories) {
        this.subCategories = subCategories;
    }
}
