package org.example.restaurant.service;

import org.example.restaurant.dto.CategoryRequest;
import org.example.restaurant.dto.CategoryResponse;
import org.example.restaurant.dto.SimpleResponse;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategory(Long categoryId);
    SimpleResponse updateCategory(CategoryRequest categoryRequest,Long categoryId);
    SimpleResponse deleteCategory(Long categoryId);
}
