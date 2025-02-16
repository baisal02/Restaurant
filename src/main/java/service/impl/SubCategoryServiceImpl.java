package service.impl;

import dto.MenuItemRequest;
import dto.SimpleResponse;
import dto.SubCategoryRequest;
import dto.SubCategoryResponse;
import entities.Category;
import entities.MenuItem;
import entities.Subcategory;
import jakarta.persistence.Entity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import repositories.SubcategoryRepo;
import service.SubCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubcategoryRepo subcategoryRepo;

    public SubCategoryServiceImpl(SubcategoryRepo subcategoryRepo) {
        this.subcategoryRepo = subcategoryRepo;
    }

    @Override
    public SimpleResponse saveSubCategory(SubCategoryRequest subCategoryRequest) {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setMessage("Success");
        simpleResponse.setStatus(HttpStatus.OK);

        if (subCategoryRequest.getName() != null) {
            return new SimpleResponse("the name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        boolean exist = subcategoryRepo.existsByName(subCategoryRequest.getName());
        if (exist) {
            return new SimpleResponse("the SubCategory already exists", HttpStatus.CONFLICT);
        }
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
        List<MenuItemRequest> menuItemRequest = subCategoryRequest.getMenuItemsRequestList();

        if (menuItemRequest != null) {
            for (MenuItemRequest menuItemReq : menuItemRequest) {
                menuItems.add(new MenuItem(menuItemReq.getName(),
                        menuItemReq.getImage(),
                        menuItemReq.getPrice(),
                        menuItemReq.getDescription(),
                        menuItemReq.isVegetarian()));
            }
            subcategory.setName(subCategoryRequest.getName());
            subcategory.setMenuItems(menuItems);
        }
        subcategoryRepo.save(subcategory);

        return new SimpleResponse("Success", HttpStatus.OK);
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
            subcategory.getMenuItems().clear();
        }
        subcategoryRepo.delete(subcategory);
        return new SimpleResponse("the subcategory has been successfully deleted", HttpStatus.NO_CONTENT);
    }
}
