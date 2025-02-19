package org.example.restaurant.api;

import org.example.restaurant.dto.CategorySortResponse;
import org.example.restaurant.dto.SimpleResponse;
import org.example.restaurant.dto.SubCategoryRequest;
import org.example.restaurant.dto.SubCategoryResponse;
import org.springframework.web.bind.annotation.*;
import org.example.restaurant.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryApi {
    private final SubCategoryService subCategoryService;

    public SubCategoryApi(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    public SimpleResponse addCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        return subCategoryService.saveSubCategory(subCategoryRequest);
    }

    @GetMapping("/{id}")
    public SubCategoryResponse getSubCategory(@PathVariable Long id) {
        return subCategoryService.getSubCategory(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateSubCategory(@RequestBody SubCategoryRequest subCategoryRequest,@PathVariable Long id) {
        return subCategoryService.updateSubCategory(subCategoryRequest, id);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteSubCategory(@PathVariable Long id) {
      return  subCategoryService.deleteSubCategory(id);
    }

    @GetMapping("/category-foods/{id}")
    public List<SubCategoryResponse> getAllSubCategories(@PathVariable Long id) {
        return subCategoryService.getSubCategories(id);
    }

    @GetMapping("/allCategories")
    public List<CategorySortResponse> getAllCategories() {
        return subCategoryService.getSorttedSubCategories();

    }

}
