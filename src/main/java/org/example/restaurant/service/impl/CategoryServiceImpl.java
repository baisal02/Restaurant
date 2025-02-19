package org.example.restaurant.service.impl;

import org.example.restaurant.dto.*;
import org.example.restaurant.entities.Category;
import org.example.restaurant.entities.MenuItem;
import org.example.restaurant.entities.Subcategory;
import jakarta.transaction.Transactional;
import org.example.restaurant.myexception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.restaurant.repositories.CategoryRepo;
import org.example.restaurant.repositories.MenuItemRepo;
import org.example.restaurant.repositories.SubcategoryRepo;
import org.example.restaurant.service.CategoryService;

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

            List<Subcategory>subcategories = new ArrayList<>();
            List<SubCategoryRequest>subCategoryRequests = categoryRequest.getSubCategoryRequests();
            if(subCategoryRequests==null) {
                return new SimpleResponse("Subcategory requests cannot be empty", HttpStatus.BAD_REQUEST);
            }
            for (SubCategoryRequest subCategoryRequest : subCategoryRequests) {
                subcategories.add(new Subcategory(subCategoryRequest.getName()));
            }

            Category category = new Category();
            category.setName(categoryRequest.getName());
            for (Subcategory subCategory : subcategories) {
                subCategory.setCategory(category);
            }
            category.setSubcategories(subcategories);
            categoryRepo.save(category);
            subcategoryRepo.saveAll(subcategories);

            return new SimpleResponse("Success", HttpStatus.OK);
        }


    @Override
    public CategoryResponse getCategory(Long categoryId){
        CategoryResponse categoryResponse = new CategoryResponse();

        Category category = categoryRepo.findById(categoryId).
                orElseThrow(()->new NotFoundException("Category doesn't exist"));

        List<Subcategory> subcategories = category.getSubcategories();
        List<String> responses = new ArrayList<>();
        for (Subcategory subcategory:subcategories){
          responses.add(subcategory.getName());
        }

        categoryResponse.setId(category.getId());
        categoryResponse.setFoodnames(responses);
        categoryResponse.setCategoryName(category.getName());
        return categoryResponse;
    }

    @Override
    public SimpleResponse updateCategory(CategoryRequest categoryRequest, Long categoryId) {

        Category category = categoryRepo.findById(categoryId).
                orElseThrow(()->new RuntimeException("Category doesn't exist"));
        List<Subcategory>subs = category.getSubcategories();
        subs.stream().forEach(subcategory -> {subcategory.setCategory(null);});
        category.setSubcategories(null);
        subcategoryRepo.saveAll(subs);
        categoryRepo.save(category);

        List<SubCategoryRequest> subCategoryRequests = categoryRequest.getSubCategoryRequests();

        if(subCategoryRequests==null){
            return new SimpleResponse("Subcategory requests cannot be empty", HttpStatus.BAD_REQUEST);
        }

        List<Subcategory> subcategories = new ArrayList<>();

        for (SubCategoryRequest subCategoryRequest1 : subCategoryRequests) {
            subcategories.add(new Subcategory(subCategoryRequest1.getName()));
        }

        for (Subcategory subCategory : subcategories) {
            subCategory.setCategory(category);
        }

        category.setSubcategories(subcategories);
        subcategoryRepo.saveAll(subcategories);
        category.setName(categoryRequest.getName());
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
