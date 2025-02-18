package api;

import dto.CategoryRequest;
import dto.CategoryResponse;
import dto.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.CategoryService;

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
