package org.example.restaurant.dto;

import java.util.List;

public class CategoryRequest {
    private String name;
    private List<SubCategoryRequest> subCategoryRequests;

    public List<SubCategoryRequest> getSubCategoryRequests() {
        return subCategoryRequests;
    }

    public void setSubCategoryRequests(List<SubCategoryRequest> subCategoryRequests) {
        this.subCategoryRequests = subCategoryRequests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryRequest() {
    }

    public CategoryRequest(String name, List<SubCategoryRequest> subCategoryRequests) {
        this.name = name;
        this.subCategoryRequests = subCategoryRequests;
    }

    public CategoryRequest(String name) {
        this.name = name;
    }
}
