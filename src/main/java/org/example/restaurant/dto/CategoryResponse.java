package org.example.restaurant.dto;

import java.util.List;

public class CategoryResponse {
    private Long id;
    private String categoryName;
    private List<String> foodnames;

    public List<String> getFoodnames() {
        return foodnames;
    }

    public void setFoodnames(List<String> foodnames) {
        this.foodnames = foodnames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
