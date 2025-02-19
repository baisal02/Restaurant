package org.example.restaurant.service.impl;

import org.example.restaurant.entities.Category;
import org.example.restaurant.entities.MenuItem;
import org.example.restaurant.entities.Subcategory;
import org.example.restaurant.dto.*;
import org.example.restaurant.repositories.MenuItemRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.restaurant.repositories.CategoryRepo;
import org.example.restaurant.repositories.SubcategoryRepo;
import org.example.restaurant.service.SubCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubcategoryRepo subcategoryRepo;
    private final CategoryRepo categoryRepo;
    private final MenuItemRepo menuItemRepo;

    public SubCategoryServiceImpl(SubcategoryRepo subcategoryRepo, CategoryRepo categoryRepo, MenuItemRepo menuItemRepo) {
        this.subcategoryRepo = subcategoryRepo;
        this.categoryRepo = categoryRepo;
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public SimpleResponse saveSubCategory(SubCategoryRequest subCategoryRequest) {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage("Success");
        simpleResponse.setStatus(HttpStatus.OK);

        if (subCategoryRequest.getName().length()==0) {
            return new SimpleResponse("the name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        boolean exist = subcategoryRepo.existsByName(subCategoryRequest.getName());
        if (exist) {
            return new SimpleResponse("the SubCategory already exists", HttpStatus.CONFLICT);
        }

        Subcategory subcategory = new Subcategory();
        subcategory.setName(subCategoryRequest.getName());
        subcategoryRepo.save(subcategory);

        return new SimpleResponse("Success", HttpStatus.OK);
    }


    @Override
    public SubCategoryResponse getSubCategory(Long subCategoryId) {
        Subcategory subcategory = subcategoryRepo.findById(subCategoryId).
                orElseThrow(() -> new RuntimeException("the SubCategory doesn't exist"));
        SubCategoryResponse subCategoryResponse = new SubCategoryResponse();
        subCategoryResponse.setId(subcategory.getId());
        subCategoryResponse.setName(subcategory.getName());
        return subCategoryResponse;
    }


    @Override
    public SimpleResponse updateSubCategory(SubCategoryRequest subCategoryRequest, Long subCategoryId) {

        Subcategory subcategory = subcategoryRepo.findById(subCategoryId).
                orElseThrow(() -> new RuntimeException("the SubCategory doesn't exist"));

        List<MenuItem> menuItems = new ArrayList<>();
        List<MenuItemRequest> menuItemRequests = subCategoryRequest.getMenuItemsRequestList();

        List<MenuItem>menuItemList = subcategory.getMenuItems();

       if(menuItemList!=null) {
       menuItemList.stream().forEach(menuItem -> {menuItem.setSubcategory(null);});
       subcategory.setMenuItems(null);
       menuItemRepo.saveAll(menuItems);
       subcategoryRepo.save(subcategory);
       }
        if (menuItemRequests != null) {

            for (MenuItemRequest menuItemReq : menuItemRequests) {
                menuItems.add(new MenuItem(menuItemReq.getName(),
                        menuItemReq.getImage(),
                        menuItemReq.getPrice(),
                        menuItemReq.getDescription(),
                        menuItemReq.isVegetarian()));
            }

            subcategory.setName(subCategoryRequest.getName());
            menuItems.stream().forEach(menuItem -> {menuItem.setSubcategory(subcategory);});
            subcategory.setMenuItems(menuItems);
            menuItemRepo.saveAll(menuItems);
            subcategoryRepo.save(subcategory);

            return new SimpleResponse("Successfully updated", HttpStatus.OK);

        } else if (menuItemRequests==null) {
            return new SimpleResponse("menuitems can't be null", HttpStatus.BAD_REQUEST);

        }

        return new SimpleResponse("Something went wrong", HttpStatus.BAD_REQUEST);


    }


    @Override
    public SimpleResponse deleteSubCategory(Long subCategoryId) {
        Subcategory subcategory = subcategoryRepo.findById(subCategoryId).
                orElseThrow(() -> new RuntimeException("Can't delete non-existing category"));

        //breaking relations before deleting
        Category category = subcategory.getCategory();

        if (category != null) {
            category.getSubcategories().remove(subcategory);
            subcategory.setCategory(null);
        }

        List<MenuItem> menuItems = subcategory.getMenuItems();
        if (menuItems != null) {
            for (MenuItem menuItem : menuItems) {
                menuItem.setSubcategory(null);
            }
            menuItemRepo.saveAll(menuItems);
            subcategory.setMenuItems(null);
        }
        subcategoryRepo.delete(subcategory);
        return new SimpleResponse("the subcategory has been successfully deleted", HttpStatus.NO_CONTENT);
    }

    @Override
    public List<SubCategoryResponse> getSubCategories(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("the Category doesn't exist"));
        List<Subcategory> subcategoryList = category.getSubcategories();

        List<SubCategoryResponse> subCategoryResponseList = new ArrayList<>();

        if (subcategoryList != null) {
            for (Subcategory subcategory : subcategoryList) {

                SubCategoryResponse subCategoryResponse = new SubCategoryResponse();

                subCategoryResponse.setId(subcategory.getId());
                subCategoryResponse.setName(subcategory.getName());

                subCategoryResponseList.add(subCategoryResponse);
            }
            return subCategoryResponseList;
        }
        return subCategoryResponseList;
    }

    @Override
    public List<CategorySortResponse> getSorttedSubCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategorySortResponse> finalList = new ArrayList<>();

        for (Category category : categories) {
            List<SubCategoryResponse> subCategoriesResponseList = new ArrayList<>();
            for (Subcategory subcategory : category.getSubcategories()) {
                subCategoriesResponseList.add(new SubCategoryResponse(
                        subcategory.getId(),
                        subcategory.getName()
                ));
            }
            finalList.add(new CategorySortResponse(category.getName(), subCategoriesResponseList));
        }
        return finalList;
    }
}
