package org.example.restaurant.service;

import org.example.restaurant.dto.CategorySortResponse;
import org.example.restaurant.dto.SimpleResponse;
import org.example.restaurant.dto.SubCategoryRequest;
import org.example.restaurant.dto.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    SimpleResponse saveSubCategory(SubCategoryRequest subCategoryRequest);
    SubCategoryResponse getSubCategory(Long subCategoryId);
    SimpleResponse updateSubCategory(SubCategoryRequest subCategoryRequest,Long subCategoryId);
    SimpleResponse deleteSubCategory(Long subCategoryId);

    List<SubCategoryResponse> getSubCategories(Long categoryId);
    List<CategorySortResponse> getSorttedSubCategories();

}
