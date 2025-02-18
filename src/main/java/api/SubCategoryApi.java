package api;

import dto.CategoryRequest;
import dto.SimpleResponse;
import dto.SubCategoryRequest;
import dto.SubCategoryResponse;
import org.springframework.web.bind.annotation.*;
import service.CategoryService;
import service.SubCategoryService;

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
}
