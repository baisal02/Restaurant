package api;

import dto.CategoryRequest;
import dto.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.CategoryService;

@RestController
@RequestMapping("/api/categories")

public class CategoryApi {

private CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public SimpleResponse getCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }
}
