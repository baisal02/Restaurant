package service;

import dto.CategoryRequest;
import dto.CategoryResponse;
import dto.SimpleResponse;
import entities.Category;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategory(Long categoryId);
    SimpleResponse updateCategory(CategoryRequest categoryRequest,Long categoryId);
    SimpleResponse deleteCategory(Long categoryId);
}
