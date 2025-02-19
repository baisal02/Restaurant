package org.example.restaurant.api;

import org.example.restaurant.dto.CategoryRequest;
import org.example.restaurant.dto.CategoryResponse;
import org.example.restaurant.dto.SimpleResponse;
import org.springframework.web.bind.annotation.*;
import org.example.restaurant.service.CategoryService;

@RestController
@RequestMapping("/api/categories")

public class CategoryApi {

    private CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public SimpleResponse addCategory(@RequestBody CategoryRequest categoryRequest) {
       return  categoryService.saveCategory(categoryRequest);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable Long id){
        return categoryService.getCategory(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryRequest,id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }

    
}
