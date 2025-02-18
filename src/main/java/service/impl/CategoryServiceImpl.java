package service.impl;

import dto.CategoryRequest;
import dto.CategoryResponse;
import dto.SimpleResponse;
import dto.SubCategoryRequest;
import entities.Category;
import entities.MenuItem;
import entities.Subcategory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repositories.CategoryRepo;
import repositories.MenuItemRepo;
import repositories.SubcategoryRepo;
import service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final SubcategoryRepo subcategoryRepo;
    private final MenuItemRepo menuItemRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo, SubcategoryRepo subcategoryRepo, MenuItemRepo menuItemRepo) {
        this.categoryRepo = categoryRepo;
        this.subcategoryRepo = subcategoryRepo;
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
            if (categoryRequest.getName() == null) {
                return new SimpleResponse("Category name cannot be empty", HttpStatus.BAD_REQUEST);
            }

            boolean exists = categoryRepo.existsByName(categoryRequest.getName());
            if (exists) {
                return new SimpleResponse("Category already exists", HttpStatus.CONFLICT);
            }

            Category category = new Category();
            category.setName(categoryRequest.getName());

            categoryRepo.save(category);

            return new SimpleResponse("Success", HttpStatus.OK);
        }


    @Override
    public CategoryResponse getCategory(Long categoryId) {
        CategoryResponse categoryResponse = new CategoryResponse();
        Category category = categoryRepo.findById(categoryId).
                orElseThrow(()->new RuntimeException("Category doesn't exist"));
        categoryResponse.setId(category.getId());
        categoryResponse.setCategoryName(category.getName());
        return categoryResponse;
    }

    @Override
    public SimpleResponse updateCategory(CategoryRequest categoryRequest, Long categoryId) {

        Category category = categoryRepo.findById(categoryId).
                orElseThrow(()->new RuntimeException("Category doesn't exist"));

        List<SubCategoryRequest> subCategoryRequest = categoryRequest.getSubCategoryRequests();

        if(subCategoryRequest != null) {
            List<Subcategory>subcategories = new ArrayList<>();
            for (SubCategoryRequest subCategoryRequest1 : subCategoryRequest) {
                subcategories.add(new Subcategory(subCategoryRequest1.getName()));
            }
            for (Subcategory subCategory : category.getSubcategories()) {
                subCategory.setCategory(category);
            }
            subcategoryRepo.saveAll(subcategories);
            category.setSubcategories(subcategories);
            category.setName(categoryRequest.getName());
        }

        categoryRepo.save(category);

        return new SimpleResponse("updated", HttpStatus.OK);
    }

    @Transactional
    @Override
    public SimpleResponse deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).
                orElseThrow(()->new RuntimeException("Can't delete non-existing category"));

        List<Subcategory>subcategories  = category.getSubcategories();
        for (Subcategory subcategory:subcategories)
        {
            for(MenuItem menuItem:subcategory.getMenuItems()){
                menuItem.setSubcategory(null);
                menuItemRepo.save(menuItem);
            }
            subcategory.getMenuItems().clear();
        }
        categoryRepo.delete(category);

       return new SimpleResponse("successfully deleted", HttpStatus.NO_CONTENT);
    }
}
