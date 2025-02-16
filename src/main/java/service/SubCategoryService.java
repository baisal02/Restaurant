package service;

import dto.CategoryRequest;
import dto.SimpleResponse;
import dto.SubCategoryRequest;
import dto.SubCategoryResponse;

public interface SubCategoryService {
    SimpleResponse saveSubCategory(SubCategoryRequest subCategoryRequest);
    SubCategoryResponse getSubCategory(Long subCategoryId);
    SimpleResponse updateSubCategory(SubCategoryRequest subCategoryRequest,Long subCategoryId);
    SimpleResponse deleteSubCategory(Long subCategoryId);
}
